package com.kspexample.processor

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import org.intellij.lang.annotations.Language
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequiresVersionProcessorTest {

    @Rule
    @JvmField
    var temporaryFolder: TemporaryFolder = TemporaryFolder()

    @Test
    fun `test`() {
        val kotlinSource = SourceFile.kotlin(
            "file1.kt", """
                package com.tests.summable

                import com.kspexample.annotation.RequiresVersion

                class Bar {
                
                    @RequiresVersion("1.2.0")
                    fun a() = "I'm not ready yet!"
                }
                
                
                class Foo {
                
                    fun b() {
                        Bar().a()
                    }
                }
    """
        )

        val compilationResult = compile(kotlinSource)

        assertEquals(KotlinCompilation.ExitCode.COMPILATION_ERROR, compilationResult.exitCode)
    }

    private fun compile(vararg source: SourceFile) = KotlinCompilation().apply {
        sources = source.toList()
        symbolProcessorProviders = listOf(RequiresVersionProcessorProvider())
        workingDir = temporaryFolder.root
        inheritClassPath = true
        verbose = false
    }.compile()
}