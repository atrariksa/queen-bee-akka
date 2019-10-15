package com.queenbee;

import static akka.pattern.Patterns.gracefulStop;

import akka.actor.*;
import akka.event.LoggingAdapter;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.IncomingConnection;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.queenbee.actors.PathMapper;
import com.queenbee.errorhandlers.ErrorHandler;
import com.typesafe.config.Config;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.queenbee.actors.MasterActor;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class QueenBee {
	
	private static final Logger logger = LoggerFactory.getLogger(QueenBee.class);
	private static String tag = "";//application name
	private static final int LIMIT_STOP_DURATION = 10;
	private boolean isRunning = false;
	private CountDownLatch endSignal = new CountDownLatch(1);
	private ActorSystem system;
	private ActorRef master;
	private Config config;
	private CompletionStage<ServerBinding> serverBindingFuture;
	private final String appName = "queenbee";
	private final String endpoints = "endpoints";
	private final String coreEngineName = "akka";
	private final String hostName = "localhost";
	private Config queenBeeConfig;
	private Config akkaConfig;

	public QueenBee () {
        this.system = ActorSystem.create(QueenBee.class.getSimpleName());
		final Http http = Http.get(system);
		final ActorMaterializer materializer = ActorMaterializer.create(system);
		final LoggingAdapter loggingAdapter = system.log();
		System.out.println("loggingAdapter.isInfoEnabled() : " + loggingAdapter.isInfoEnabled());
		Source<IncomingConnection, CompletionStage<ServerBinding>> serverSource =
				http.bind(ConnectHttp.toHost(hostName, 9789));

		ErrorHandler errorHandler = new ErrorHandler();
		tag = "[" + system.name() + "] ";
		akkaConfig = system.settings().config().getConfig(coreEngineName);
		queenBeeConfig = system.settings().config().getConfig(appName);

		PathMapper pathMapper = new PathMapper(errorHandler, queenBeeConfig);

		serverBindingFuture = serverSource.to(Sink.foreach(connection -> {
			System.out.println("Accepted new connection from " + connection.remoteAddress());
			connection.handleWith(Flow.of(HttpRequest.class).mapAsync(1, pathMapper.mapAsyncHandler()), materializer);
		})).run(materializer);
		try {
			System.in.read();
		}
		catch (Exception e) {

		}
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
		logger.info("Application has been terminated..");
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
			serverBindingFuture
				.thenCompose(serverBinding -> {
					logger.info(tag + "Unbinding..");
					return serverBinding.unbind();
				}) // trigger unbinding from the port
				.thenAccept(unbound -> {
					Future<Boolean> stopped = gracefulStop(master, Duration.create(LIMIT_STOP_DURATION, TimeUnit.SECONDS));
					try {
						Await.result(stopped, Duration.create(LIMIT_STOP_DURATION, TimeUnit.SECONDS));
					} catch (Exception e) {
						logger.error("Failed to gracefully stop master actor..");
					}
					system.terminate();
					logger.info(tag + "Terminating system..");
				}).exceptionally(throwable -> {
					logger.error(tag + throwable.getLocalizedMessage());
					return null;
				}
			).whenComplete((action, throwable)->{
				logger.info(tag + "bye bye..");
				isRunning = false;
			}); // and shutdown when done
		} catch (Exception ex) {
			logger.error(tag + "Failed to shutdown properly.."+ex);
		}
        isRunning = false;
    }
}

