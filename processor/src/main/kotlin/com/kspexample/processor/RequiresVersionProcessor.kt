package com.kspexample.processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.kspexample.annotation.RequiresVersion

class RequiresVersionProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(RequiresVersion::class.qualifiedName!!)

        symbols.forEach { it.accept(Visitor(), Unit) }

        return emptyList()
    }

    private inner class Visitor : KSVisitorVoid() {

        override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            super.visitFunctionDeclaration(function, data)
            logger.error("Annotated function name: ${function.qualifiedName?.getShortName()}")
        }
    }
}
