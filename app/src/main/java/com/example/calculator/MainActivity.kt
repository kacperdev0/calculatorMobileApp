package com.example.calculator

import android.os.Bundle
import android.util.Log
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
                    MainContent();
                }
            }
        }
    }
}

@Composable
fun MainContent() {

    var artmetics = setOf("/", "*", "+", "-", ".")

    var placeholder by remember {
        mutableStateOf("");
    }

    fun evaluateExpression() {
        if (artmetics.contains(placeholder.last().toString())) {
            placeholder = placeholder.dropLast(1)
        }

        val e : Expression = Expression(placeholder);
        var result = e.calculate()
        placeholder = e.calculate().toString().removeSuffix(".0");
    }

    fun addNumberToPlaceholder(value: String) {
        placeholder = placeholder + value;
    }

    fun addArtmeticSymbolToPlaceholder(value: String) {
        var lastChar = placeholder.last().toString()
        var penulimateChar = placeholder[placeholder.length-2].toString()

        if (placeholder.isEmpty()) return

        if (artmetics.contains(lastChar))
        {
            if (lastChar in setOf(".","+","-")) {
                if (penulimateChar in artmetics) {
                    placeholder = placeholder.dropLast(2) + value
                } else {
                    placeholder = placeholder.dropLast(1) + value
                }
            } else {
                if (value == "-") {
                    placeholder = placeholder + value
                }
            }
        }
        else
        {
            placeholder = placeholder + value
        }
    }

    fun getPlaceholder(): String {
        if (placeholder == "") {
            return "0";
        } else {
            return placeholder
        }
    }

    fun addDecimalPart() {
        val operands = placeholder.split(Regex("[+\\-*/]"))
        var lastNumber = operands.last().toString()

        if ("." !in lastNumber && lastNumber != "") { placeholder = placeholder + '.' }
    }

    fun addZeroToPlaceholder() {
        if (placeholder != "" && !artmetics.contains(placeholder.last().toString())) {
            placeholder = placeholder + "0"
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
            SingleButton(label = "AC", onClick = { placeholder = "" })
            SingleButton(label = "+/-", onClick = { placeholder = placeholder.split(Regex("[+-/*]")).toString() })
            SingleButton(label = "%")
            SingleButton(label = "/", onClick = { addArtmeticSymbolToPlaceholder("/") })
        }
        Row {
            SingleButton(label = "7", onClick = { addNumberToPlaceholder("7")})
            SingleButton(label = "8", onClick = { addNumberToPlaceholder("8")})
            SingleButton(label = "9", onClick = { addNumberToPlaceholder("9")})
            SingleButton(label = "x", onClick = { addArtmeticSymbolToPlaceholder("*") })
        }
        Row {
            SingleButton(label = "4", onClick = { addNumberToPlaceholder("4")})
            SingleButton(label = "5", onClick = { addNumberToPlaceholder("5")})
            SingleButton(label = "6", onClick = { addNumberToPlaceholder("6")})
            SingleButton(label = "-", onClick = { addArtmeticSymbolToPlaceholder("-") })
        }
        Row {
            SingleButton(label = "1", onClick = { addNumberToPlaceholder("1")})
            SingleButton(label = "2", onClick = { addNumberToPlaceholder("2")})
            SingleButton(label = "3", onClick = { addNumberToPlaceholder("3")})
            SingleButton(label = "+", onClick = { addArtmeticSymbolToPlaceholder("+") })
        }
        Row {
            SingleButton(label = "0", onClick = { addZeroToPlaceholder() }, width = 200.dp)
            SingleButton(label = ",", onClick = { addDecimalPart() })
            SingleButton(label = "=", onClick = { evaluateExpression() })
        }
    }
}

@Composable
fun SingleButton(label: String, onClick: () -> Unit = {}, width: Dp = 100.dp) {
    Button (
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
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