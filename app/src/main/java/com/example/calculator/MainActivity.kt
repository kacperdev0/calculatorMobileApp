package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import org.mariuszgromada.math.mxparser.*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {

            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {

    val artmetics = setOf("/", "*", "+", "-", ".")
    val numbers = setOf("1","2","3","4","5","6","7","8","9","0")

    var placeholder by remember {
        mutableStateOf("")
    }

    val themeColor = Color(232, 135, 31)

    fun evaluateExpression() {
        if (artmetics.contains(placeholder.last().toString())) {
            placeholder = placeholder.dropLast(1)
        }

        val e = Expression(placeholder)
        placeholder = e.calculate().toString().removeSuffix(".0")
    }

    fun addNumberToPlaceholder(value: String) {
        placeholder += value
    }

    fun addArtmeticSymbolToPlaceholder(value: String) {
        val lastChar = placeholder.last().toString()
        val penulimateChar = placeholder[placeholder.length-2].toString()

        if (placeholder.isEmpty()) return

        if (artmetics.contains(lastChar))
        {
            if (lastChar in setOf(".","+","-")) {
                placeholder = if (penulimateChar in artmetics) {
                    placeholder.dropLast(2) + value
                } else {
                    placeholder.dropLast(1) + value
                }
            } else {
                if (value == "-") {
                    placeholder += value
                }
            }
        }
        else
        {
            placeholder += value
        }
    }

    fun getPlaceholder(): String {
        return if (placeholder == "") {
            "0"
        } else {
            placeholder
        }
    }

    fun addDecimalPart() {
        val operands = placeholder.split(Regex("[+\\-*/]"))
        val lastNumber = operands.last().toString()

        if ("." !in lastNumber && lastNumber != "") {
            placeholder += '.'
        }
    }

    fun addZeroToPlaceholder() {
        val lastDigit = placeholder.last().toString()
        if (placeholder != "" && !artmetics.contains(lastDigit)) {
            placeholder += "0"
        }
    }

    fun handleSignChange() {
        for (i in placeholder.length - 1 downTo 0) {
            if (placeholder[i].toString() in artmetics) {
                placeholder = if (placeholder[i-1].toString() in artmetics) {
                    placeholder.substring(0, i) + placeholder.substring(i+1, placeholder.length)
                } else {
                    placeholder.substring(0, i + 1) + "-" + placeholder.substring(i+1, placeholder.length)
                }
                break
            }
        }
    }

    fun addPercentSymbol() {
        val lastDidgit = placeholder.last().toString()

        if (lastDidgit in numbers) {
            placeholder += "%"
        }
    }


    Column {
        Text(
            text = getPlaceholder(),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp),
            fontSize = 30.sp

        )
        Row {
            SingleButton(label = "AC", onClick = { placeholder = "" }, bgColor = themeColor)
            SingleButton(label = "+/-", onClick = { handleSignChange() }, bgColor = themeColor)
            SingleButton(label = "%", onClick = { addPercentSymbol() }, bgColor = themeColor)
            SingleButton(label = "/", onClick = { addArtmeticSymbolToPlaceholder("/") }, bgColor = themeColor)
        }
        Row {
            SingleButton(label = "7", onClick = { addNumberToPlaceholder("7")})
            SingleButton(label = "8", onClick = { addNumberToPlaceholder("8")})
            SingleButton(label = "9", onClick = { addNumberToPlaceholder("9")})
            SingleButton(label = "x", onClick = { addArtmeticSymbolToPlaceholder("*") }, bgColor = themeColor)
        }
        Row {
            SingleButton(label = "4", onClick = { addNumberToPlaceholder("4")})
            SingleButton(label = "5", onClick = { addNumberToPlaceholder("5")})
            SingleButton(label = "6", onClick = { addNumberToPlaceholder("6")})
            SingleButton(label = "-", onClick = { addArtmeticSymbolToPlaceholder("-") }, bgColor = themeColor)
        }
        Row {
            SingleButton(label = "1", onClick = { addNumberToPlaceholder("1")})
            SingleButton(label = "2", onClick = { addNumberToPlaceholder("2")})
            SingleButton(label = "3", onClick = { addNumberToPlaceholder("3")})
            SingleButton(label = "+", onClick = { addArtmeticSymbolToPlaceholder("+") }, bgColor = themeColor)
        }
        Row {
            SingleButton(label = "0", onClick = { addZeroToPlaceholder() }, width = 200.dp)
            SingleButton(label = ",", onClick = { addDecimalPart() })
            SingleButton(label = "=", onClick = { evaluateExpression() }, bgColor = themeColor)
        }
    }
}

@Composable
fun SingleButton(label: String, onClick: () -> Unit = {}, width: Dp = 100.dp, bgColor: Color = Color.DarkGray) {
    Button (
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(width)
            .height(100.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(
            text = label,
            fontSize = 30.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        MainActivity()
    }
}