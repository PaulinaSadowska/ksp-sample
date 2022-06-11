package com.kspexample.processor

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class RequiresVersion(val version: String)


class Bar {

    @RequiresVersion("1.2.0")
    fun a() = "I'm not ready yet!"
}


class Foo {

    fun b() {
        Bar().a()
    }
}