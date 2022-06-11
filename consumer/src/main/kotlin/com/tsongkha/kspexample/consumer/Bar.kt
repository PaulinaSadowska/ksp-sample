import com.kspexample.annotation.RequiresVersion

class Bar {
    @RequiresVersion("1.2.0")
    fun a() = "I'm not ready yet!"
}