package com.circuit.cmp.di.module

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

@Module(
    includes = [
        CircuitModule::class
    ]
)
@ComponentScan
class AppModule {

}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        printLogger()
        modules(
            AppModule().module
        )
    }
}
