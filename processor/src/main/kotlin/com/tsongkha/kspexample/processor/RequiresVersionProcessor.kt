package com.tsongkha.kspexample.processor

import com.google.devtools.ksp.*
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.visitor.KSTopDownVisitor
import com.tsongkha.kspexample.annotation.RequiresVersion

class RequiresVersionProcessor(
    private val options: Map<String, String>,
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private lateinit var intType: KSType

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        intType = resolver.builtIns.intType
        val annotated = resolver.getSymbolsWithAnnotation(RequiresVersion::class.qualifiedName!!).toList()

        annotated.forEach {
            logger.error("Annotated ${annotated.size} $it class ${(it.location as FileLocation).lineNumber}")

            if(it is KSFunctionDeclaration){
                //logger.error("Hello $it")
            }
            val fileLocation = (it.location as FileLocation)
           // logger.error("fileLocation ${fileLocation.filePath}")
            //val ksname = resolver.getKSNameFromString("Bar.kt")
            val ksname = resolver.getKSNameFromString(fileLocation.
            filePath.split("/").last())

            //logger.error("KSNAME ${ksname.asString()}")

            val clazzDeclaration = resolver.getKotlinClassByName(ksname)
            if (clazzDeclaration == null) {
                logger.error("clazz not found")
                } else {
                clazzDeclaration.getAllFunctions().forEach {
                    //logger.error("function ${(it.location as FileLocation).lineNumber}")
                }
            }
        }



        val symbols = resolver.getAllFiles()

        val unableToProcess = symbols.filterNot { it.validate() }

        symbols
            .filter { it.validate() }
            .forEach { it.accept(Visitor(), annotated) }

        return unableToProcess.toList()
    }

    private inner class Visitor : KSTopDownVisitor<List<KSAnnotated>, Unit>() {

        /*override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
            super.visitFunctionDeclaration(function, data)
            logger.error("FUNC ${function.simpleName}")
        }*/

        override fun defaultHandler(node: KSNode, data: List<KSAnnotated>) {

            //logger.error("node ${(node.location as? FileLocation)?.filePath?.split("/")?.last()}")
            //logger.error("data ${data.first()}")
val isMain = (node.location as? FileLocation)?.filePath?.split("/")?.last() == "Main.kt"
            var logged = false
            if (isMain) {
                //if (!logged) {
                    //logged = true
                logger.error("isMain  ${node::class}")
                //    if (node is KSFunctionDeclaration) {
                        //logger.error("declarations + ${node.simpleName.getShortName()}")
                  //  }
                if (node is KSFile) {
                    logger.error("declarations + ${node}")
                }

               // }
            }

            if (node is KSClassDeclaration) {
             //  node..forEach {
               //     logger.error("prop $it " +
                 //           "${(node.location as FileLocation).filePath.split("/").last()}")
                }

            }

            // data.forEach {
               // if (node is KSTypeReference) {
                   // val element = node.parent



                   // if(it.toString() == node.toString()){
                        //logger.error("parent $element node $node name ${(node.location as? FileLocation)?.lineNumber} ${node::class.simpleName}")
                    //}
              //  }


           // }

    }
}
