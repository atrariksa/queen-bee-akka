package com.queenbee.annotations;

import com.queenbee.annotations.customs.RunThis;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TypeAnnotationScanner extends AnnotationScanner implements ITypeAnnotationScanner{
    private List<String> listClass = new ArrayList<>();
    private String[] classes;
    private String rootPackage;

    public TypeAnnotationScanner() {

    }

    public TypeAnnotationScanner(String rootPackage) {
        this.rootPackage = rootPackage;
    }

    public static void main(String[] args) {
        TypeAnnotationScanner typeAnnotationScanner = new TypeAnnotationScanner();
        typeAnnotationScanner.scan();
        String[] classes = typeAnnotationScanner.getAllClases();
        for (int i = 0; i < classes.length; i++) {
            System.out.println("Fully qualified class name : " + classes[i]);
        }
    }

    @Override
    public void scan() {
        String javaCommand = "sun.java.command";
        if (rootPackage != null && !rootPackage.equals("")) {
            javaCommand = rootPackage;
        }
        String[] splittedJavaCommand = System.getProperty(javaCommand).split("\\.");
        String mainPackage = "";
        for (int i = 0; i < splittedJavaCommand.length-1; i++) {
            mainPackage = mainPackage + splittedJavaCommand[i] + ".";
        }
        mainPackage = mainPackage.substring(0, mainPackage.length()-1);
        Set<Class<?>> classes = new Reflections(mainPackage, new TypeAnnotationsScanner(), new SubTypesScanner()).getTypesAnnotatedWith(RunThis.class);
        classes.forEach(clazz -> {
            listClass.add(clazz.getName());
        });

        new Reflections(mainPackage, new TypeAnnotationsScanner(), new SubTypesScanner());
    }

    @Override
    public String[] getAllClases() {
        if (classes == null) {
            classes = listClass.toArray(new String[listClass.size()]);
        }
        return classes;
    }
    /*
    Set<Class<?>> classes = new Reflections("com.queenbee.annotations", new TypeAnnotationsScanner(), new SubTypesScanner()).getTypesAnnotatedWith(RunThis.class);
        classes.forEach(clazz -> {
            Class<? extends Action<?>>[] validators = clazz.getAnnotation(RunThis.class).value();
            Class<? extends Action<?>> validator = validators[0];
//            EventSource eventSource = new EventSource();
            try {
                Method[] methods = clazz.getDeclaredMethods();
                Method method = null;
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].getName().equals("addObserver")) {
                        method = methods[i];
                        break;
                    }
                }
                clazzResult = clazz.newInstance();
                method.invoke(clazzResult, validator.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }  catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
     */
}
