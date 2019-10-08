package com.queenbee;

import static akka.pattern.Patterns.gracefulStop;

import akka.NotUsed;
import akka.actor.*;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.routing.FromConfig;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import com.queenbee.controllers.HomeController;
import com.queenbee.errorhandlers.ErrorHandler;
import com.queenbee.workers.*;
import com.typesafe.config.Config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.queenbee.actors.MasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

//import javax.inject.Inject;

public class QueenBee extends AllDirectives {
	
	private static final Logger logger = LoggerFactory.getLogger(QueenBee.class);
	private static String tag = "";//application name
	private static final int LIMIT_STOP_DURATION = 10;
	private boolean isRunning = false;
	private CountDownLatch endSignal = new CountDownLatch(1);
	private ActorSystem system;
	private ActorRef master;
	private Config config;
	private List<Route> routes = new ArrayList<>();

	public QueenBee () {
        this.system = ActorSystem.create(QueenBee.class.getSimpleName());
//        ActorRef homeController = system.actorOf(Props.create(HomeController.class), "home_worker");
//        homeController.tell(new Work("Work1"), ActorRef.noSender());

		//style 1 & 2
        ActorRef actorConsumerTest = system.actorOf(Props.create(ActorConsumerTest.class), "actor_consumer");
        actorConsumerTest.tell(new Work("Work1"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work2"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work3"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work4"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work5"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work6"), ActorRef.noSender());
		actorConsumerTest.tell(new Work("Work7"), ActorRef.noSender());


		final Http http = Http.get(system);

		final ActorMaterializer materializer = ActorMaterializer.create(system);

		logger.info(tag + "materializer.supervisor().toString() : " + materializer.supervisor().toString());

		//In order to access all directives we need an instance where the routes are define.
//		QueenBee app = new QueenBee();

		ErrorHandler errorHandler = new ErrorHandler(this);

		final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = this.createRoute(errorHandler).flow(system, materializer);
		final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
				ConnectHttp.toHost("localhost", 9789), materializer);

		System.out.println("Server online at http://localhost:9789/\nPress RETURN to stop...");
		this.config = system.settings().config().getConfig("akka");
		tag = "[" + system.name() + "] ";
		logger.info(tag + this.config);
		try {
			System.in.read(); // let it run until user presses return
		} catch (IOException e) {
			e.printStackTrace();
		}

		binding
				.thenCompose(ServerBinding::unbind) // trigger unbinding from the port
				.thenAccept(unbound -> system.terminate()); // and shutdown when done

    }
    public void addRoute(Route route) {
		routes.add(route);
	}
	private Route createRoute(ErrorHandler errorHandler) {
		return concat(
				path("hello", () ->
						get(() ->
								errorHandler.getBadRequest())));
	}

//    @Inject()
	public QueenBee(Config config) {
        this.config = config;
		this.system = ActorSystem.create(QueenBee.class.getSimpleName());
		tag = "[" + system.name() + "] ";
	}
	
    public static void main(String[] args) throws Exception {
        Config conf = ConfigFactory.load();
        for (int a = 0; a < args.length; a++) {
            System.out.println("Args[" + a + "] :" + args[a]);
        }

        final QueenBee m = new QueenBee();

        Thread shutdownThread = new Thread() { //hook the shutdown of the threads
            @Override
            public void run() {
                try {
                    if (m.isRunning) {
                        logger.debug("Shutting down application...");
                        m.destroy();
                    }
                } catch (Exception ex) {
                    logger.error("Normal shutdown/kill failed ", ex);
                }

                Runtime.getRuntime().removeShutdownHook(this);
                System.exit(0);
            }
        };
        Runtime.getRuntime().addShutdownHook(shutdownThread);

        logger.debug(tag + "Turning on System..");
		try {
			try {
				m.start();
				logger.debug(tag + "is Listening..");
				m.endSignal.await();
				m.destroy();
			} catch (InstantiationException e) {
				logger.error(tag + e.toString());
			} catch (IllegalAccessException e) {
				logger.error(tag + e.toString());
			} catch (ClassNotFoundException e) {
				logger.error(tag + e.toString());
			} catch (SQLException e) {
				logger.error(tag + e.toString());
			}
		} catch (IOException e) {
			logger.error(tag + "System Failed to Start.." + e.toString());
		} catch (Exception e) {
			logger.error(tag + "System Failed to Start.." + e.toString());
		}
		logger.debug("Application is complete, goodbye...");
		Runtime.getRuntime().removeShutdownHook(shutdownThread);
		System.exit(0);
    }

	public void start() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		logger.debug(tag + "Starting Engine..");
		preStart();
		isRunning = true;
	}

	private void preStart() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		logger.debug(tag + "Initializing System. Please wait..");

		master = system.actorOf(Props.create(MasterActor.class, system, this.config), "MasterActor");
	}

    @SuppressWarnings("deprecation")
	public void destroy() throws Exception {
		try {
			Future<Boolean> stopped = gracefulStop(master, Duration.create(LIMIT_STOP_DURATION, TimeUnit.SECONDS));
			Await.result(stopped, Duration.create(LIMIT_STOP_DURATION, TimeUnit.SECONDS));
			logger.info(tag + "Shutdown..");
		} catch (Exception ex) {
			logger.error(tag + "Failed to shutdown properly.."+ex);
		}
		system.terminate();
        isRunning = false;
    }
}

