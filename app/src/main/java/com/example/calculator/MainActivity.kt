package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calculator.ui.theme.CalculatorTheme

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
                    Column {
                        Row {
                            SingleButton(label = "AC")
                            SingleButton(label = "+/-")
                            SingleButton(label = "%")
                            SingleButton(label = "/")
                        }
                        Row {
                            SingleButton(label = "7")
                            SingleButton(label = "8")
                            SingleButton(label = "9")
                            SingleButton(label = "x")
                        }
                        Row {
                            SingleButton(label = "4")
                            SingleButton(label = "5")
                            SingleButton(label = "6")
                            SingleButton(label = "-")
                        }
                        Row {
                            SingleButton(label = "1")
                            SingleButton(label = "2")
                            SingleButton(label = "3")
                            SingleButton(label = "+")
                        }
                        Row {
                            SingleButton(label = "0", width = 200.dp)
                            SingleButton(label = ",")
                            SingleButton(label = "=")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SingleButton(label: String, onClick: () -> Unit = {}, width: Dp = 100.dp) {
    Button (
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Blue
        ),
        modifier = Modifier
            .width(width)
            .height(100.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        MainActivity()
    }
}