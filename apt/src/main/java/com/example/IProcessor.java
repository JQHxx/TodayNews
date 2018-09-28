package com.example;

import javax.annotation.processing.RoundEnvironment;

/**
 * Created by anson on 2018/8/30.
 */
public interface IProcessor {
    void process(RoundEnvironment roundEnv, AnnotationProcessor mAbstractProcessor);
}
