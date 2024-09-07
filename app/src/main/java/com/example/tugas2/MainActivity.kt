package com.example.tugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tugas2.ui.theme.Tugas2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tugas2Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "calculator_screen"
                ) {
                    composable("calculator_screen") { CalculatorScreen(navController) }
                    composable("result_screen/{result}") { backStackEntry ->
                        ResultScreen(
                            result = backStackEntry.arguments?.getString("result") ?: "0",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(navController: NavHostController) {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf("+") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = input1,
            onValueChange = { input1 = it },
            label = { Text("Bilangan A") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = input2,
            onValueChange = { input2 = it },
            label = { Text("Bilangan B") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            RadioButton(
                selected = selectedOperation == "+",
                onClick = { selectedOperation = "+" }
            )
            Text("+")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedOperation == "-",
                onClick = { selectedOperation = "-" }
            )
            Text("-")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedOperation == "*",
                onClick = { selectedOperation = "*" }
            )
            Text("*")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedOperation == "/",
                onClick = { selectedOperation = "/" }
            )
            Text("/")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val result = calculateResult(input1, input2, selectedOperation)
                navController.navigate("result_screen/$result")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hitung")
        }
    }
}

fun calculateResult(input1: String, input2: String, operation: String): String {
    val num1 = input1.toDoubleOrNull() ?: 0.0
    val num2 = input2.toDoubleOrNull() ?: 0.0
    return when (operation) {
        "+" -> (num1 + num2).toString()
        "-" -> (num1 - num2).toString()
        "*" -> (num1 * num2).toString()
        "/" -> if (num2 != 0.0) (num1 / num2).toString() else "Error: Division by Zero"
        else -> "Invalid Operation"
    }
}

@Composable
fun ResultScreen(result: String, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Hasil: $result")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Nama: Vraynannisa")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "NIM: 225150400111036")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Kembali")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    Tugas2Theme {
        CalculatorScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    Tugas2Theme {
        ResultScreen("Result", rememberNavController())
    }
}