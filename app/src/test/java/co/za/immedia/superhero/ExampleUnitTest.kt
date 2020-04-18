package co.za.immedia.superhero

import co.za.immedia.superhero.Model.ExpandedItem
import co.za.immedia.superhero.Model.Powerstats
import org.junit.AfterClass
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import kotlin.reflect.full.memberProperties


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {

    companion object {

        var powersts: Powerstats? = null

        //Static
        var powerstatsList = mutableListOf<ExpandedItem>()

        @BeforeClass
        @JvmStatic
        fun setup() {

            powersts = Powerstats(
                power = "20",
                intelligence = "30",
                speed = "40",
                durability = "50",
                strength = "50",
                combat = "null"
            )
            // things to execute once and keep around for the class
            powerstatsList.add(ExpandedItem(key = "power", value = "20"))
            powerstatsList.add(ExpandedItem(key = "intelligence", value = "30"))
            powerstatsList.add(ExpandedItem(key = "speed", value = "40"))
            powerstatsList.add(ExpandedItem(key = "durability", value = "50"))
            powerstatsList.add(ExpandedItem(key = "strength", value = "50"))
            powerstatsList.add(ExpandedItem(key = "combat", value = "null"))
        }

        @AfterClass
        fun finish() {
            powerstatsList.clear()
            powersts = null
        }
    }



    @Test
    fun getItems() {
        powerstatsList.clear()
        for (prop in powersts!!::class.memberProperties) {
            val name = powersts?.let {
                powersts!!.javaClass.kotlin.memberProperties.first { it.name == prop.name }
                    .get(it)
            }
            powerstatsList.add(ExpandedItem(key = prop.name, value = name.toString()))
        }
        assertTrue(powerstatsList.size > 0)
    }

    @Test
    fun overrallScore() {
        var value = 0
        for (item in powerstatsList) {
            if (item.value != "null") {
                value = value + item.value.toInt()
            }
        }
        assertEquals(190, value)
    }
}
