package com.tencent.router.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.tencent.annotation.Router;

import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by weiersyuan on 2017/9/6.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes("com.tencent.annotation.Router")
public class RouterProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateDefaultRouterInit(processingEnv.getFiler());
        Set<? extends Element> routerElementSet = roundEnv.getElementsAnnotatedWith(Router.class);
        MethodSpec.Builder mapMethod = MethodSpec.methodBuilder("addAllActivity")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addCode("\n");

        for (Element element : routerElementSet) {
            Router router = element.getAnnotation(Router.class);
            for (String uriString : router.value()) {
                ClassName className;
                if (element.getKind() == ElementKind.CLASS) {
                    // 只处理对类的注解
                    className = ClassName.get((TypeElement) element);
                } else {
                    throw new IllegalArgumentException("illegal target");
                }

                mapMethod.addStatement("com.tencent.routerdispatch.Router.addActivity($S, $T.class)", uriString, className);
            }
        }

        TypeSpec routerMapping = TypeSpec.classBuilder("RouterCollection")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mapMethod.build())
                .build();

        try {
            JavaFile.builder("com.tencent.routerdemo.router", routerMapping)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return true;
    }

    private void generateDefaultRouterInit(Filer filer) {
        MethodSpec.Builder initMethod = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC);
        initMethod.addStatement("com.tencent.routerdemo.router.RouterCollection.addAllActivity()");
        TypeSpec routerInit = TypeSpec.classBuilder("RouterInit")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(initMethod.build())
                .build();
        try {
            JavaFile.builder("com.tencent.router.api", routerInit)
                    .build()
                    .writeTo(filer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
