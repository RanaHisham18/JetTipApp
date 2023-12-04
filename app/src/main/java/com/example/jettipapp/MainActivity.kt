@file:OptIn(
    ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class,
    ExperimentalComposeUiApi::class
)

package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import  com.example.jettipapp.components.InputField
import com.example.jettipapp.util.calculateTotalTip
import com.example.jettipapp.widgets.RoundedIconButton

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication {
                MainContent()
            }
        }
    }
}

@Preview
@Composable
fun TopHeaderCard(totalPerPerson: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(15.dp)),
        color = Color(0xFFF09AFF)
    ) {
        val value = "%.2f".format(totalPerPerson)
        Column(
            modifier = Modifier.padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Total Per Person", style = MaterialTheme.typography.titleMedium)
            Text(
                text = "$$totalPerPerson", style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun MyApplication(content: @Composable () -> Unit) {
//expects another composable function
    Surface(color = MaterialTheme.colorScheme.background) {

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun MainContent() {
    BillState()


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillState(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {}
) {


    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }
    val tipPercentage = (sliderPositionState.value * 100).toInt()
    val splitState = remember {
        mutableStateOf(1)
    }
    val tipAmountState = remember {
        mutableStateOf(0.0)
    }
    val range = IntRange(start = 1, endInclusive = 100)
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        TopHeaderCard()



        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                InputField(
                    valueState = totalBillState,
                    labelId = "Enter Bill",
                    enabled = true,
                    isSingleLine = true,
                    onAction = KeyboardActions {
                        if (!validState) return@KeyboardActions
                        onValChange(totalBillState.value.trim())
                        keyboardController?.hide()
                    }
                )

                // if (validState) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Split",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(120.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundedIconButton(

                            imageVector = Icons.Default.Remove,
                            onClick = {

                                splitState.value =
                                    if (splitState.value > 1) splitState.value - 1
                                    else 1
                            })

                        Text(
                            text = "${splitState.value}", modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )

                        RoundedIconButton(

                            imageVector = Icons.Default.Add,
                            onClick = {
                                if (splitState.value < range.last) {
                                    splitState.value += 1
                                }
                            })


                    };
                }


                //tip row
                Row(modifier = Modifier.padding(horizontal = 3.dp)) {

                    Text(text = "Tip", modifier = Modifier.align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text(
                        text = "$ ${tipAmountState.value}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                }
                Column {
                    Text(text = "$tipPercentage %", modifier = Modifier.align(Alignment.CenterHorizontally))
                

                Slider(value = sliderPositionState.value, onValueChange = { newVal ->
                    sliderPositionState.value = newVal
                    //tochange the slider value and position

                    tipAmountState.value = calculateTotalTip(totalBill = totalBillState.value.toDouble(), tipPercentage = tipPercentage)
                },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 5,
                    onValueChangeFinished = {

                    }) }

//            } else {
//                Box {
//
//                }
            }
        }
    }
}





