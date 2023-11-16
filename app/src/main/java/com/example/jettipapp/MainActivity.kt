package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jettipapp.ui.theme.JetTipAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication {

            }
        }
    }
}

@Preview
@Composable
fun TopHeaderCard(totalPerPerson : Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(shape = RoundedCornerShape(15.dp)),
        color = Color(0xFFb392de)
    ) {
        val value = "%.2f".format(totalPerPerson)
        Column (modifier = Modifier.padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = "Total Per Person", style = MaterialTheme.typography.titleMedium)
            Text(text = "$$totalPerPerson", style = MaterialTheme.typography.titleSmall,
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipAppTheme {
        MyApplication {
            Text(text = "Hello")
        }
    }
}