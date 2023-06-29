package com.cd.utility.text

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cd.utility.R
import com.cd.utility.color.*

val poppins_semiBold = FontFamily(Font(R.font.poppins_semibold))
val poppins_regular = FontFamily(Font(R.font.poppins_regular))
val poppins_medium = FontFamily(Font(R.font.poppins_medium))

@Composable
fun Text_Poppins_SemiBold_16dp_Black(text: String){
    Text(text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = black,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_14dp_Black(text: String){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = black,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_14dp_DarkBlack(text: String){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextColor,
        fontFamily = poppins_semiBold
    )
}


@Composable
fun Text_Poppins_SemiBold_16dp_PrimaryColor(text:String){
    Text(text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = primaryColor,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_10dp_PrimaryColor(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        color = primaryColor,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_12dp_PrimaryColor(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = primaryColor,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_Regular_12dp_Black(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = black,
        fontFamily = poppins_regular
    )
}

@Composable
fun Text_Poppins_Regular_12dp_BlackText(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = blackText,
        fontFamily = poppins_regular
    )
}
@Composable
fun Text_Poppins_SemiBold_12dp_BlackText(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = blackText,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_28dp_Black(text:String){
    Text(text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        color = black,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_12dp_Black(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextColor,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_SemiBold_12dp_Red(text:String, textSize : TextUnit = 12.sp){
    Text(text = text,
        fontSize = textSize,
        fontWeight = FontWeight.SemiBold,
        color = red,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun ClickableText_Poppins_Medium_14dp_lightGray(
    text: String,
    type : String,
    onClick : (String) ->Unit){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = lightGray,
        fontFamily = poppins_medium,
        modifier = Modifier
            .clickable {
                onClick(type)
            }
            .padding(start = 20.dp, end = 20.dp)
    )
}

@Composable
fun Text_Poppins_Medium_10dp_lightGray(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = lightGray,
        fontFamily = poppins_medium,
    )
}

@Composable
fun Text_Poppins_Regular_10dp_white(text:String){
    Text(text = text,
        modifier = Modifier.height(16.dp),
        fontSize = 10.sp,
        color = Color.White,
        fontFamily = poppins_regular
    )
}

@Composable
fun Text_Poppins_Regular_10dp_Black(text:String){
    Text(text = text,
        modifier = Modifier.height(16.dp),
        fontSize = 10.sp,
        color = Color.Black,
        fontFamily = poppins_regular
    )
}

@Composable
fun Text_Poppins_Medium_14dp_Black(text:String){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = black,
        fontFamily = poppins_medium
    )
}

@Composable
fun Text_Poppins_Medium_14dp_BlackText(text:String){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = blackText,
        fontFamily = poppins_medium
    )
}


@Composable
fun Text_Poppins_Medium_10dp_Gray(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = gray,
        fontFamily = poppins_medium
    )
}

@Composable
fun Text_Poppins_Medium_10dp_Black(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = blackText,
        fontFamily = poppins_medium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Text_Poppins_Medium_10dp_lightGray_Center(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = lightGray,
        fontFamily = poppins_medium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Text_Poppins_Medium_10dp_white(text:String){
    Text(text = text,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = TextWhite,
        fontFamily = poppins_medium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun Text_Poppins_SemiBold_14dp_white(text:String){
    Text(text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextWhite,
        fontFamily = poppins_regular
    )
}
@Composable
fun TopAppBar_Poppins_SemiBold_16dp_Black(text: String,textSize : TextUnit = 16.sp){
    Text(text = text,
        fontSize = textSize,
        fontWeight = FontWeight.SemiBold,
        color = Color.Black,
        fontFamily = poppins_semiBold
    )
}
@Composable
fun TopAppBar_Poppins_SemiBold_16dp_Grey(text: String,textSize : TextUnit = 16.sp){
    Text(text = text,
        fontSize = textSize,
        fontWeight = FontWeight.SemiBold,
        color = Color.Gray,
        fontFamily = poppins_semiBold
    )
}

@Composable
fun Text_Poppins_Medium_12dp_lightGray(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = lightGray,
        fontFamily = poppins_regular
    )
}

@Composable
fun Text_Poppins_Medium_12dp_BlackText(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = blackText,
        fontFamily = poppins_regular,
    )
}

@Composable
fun Text_Poppins_Medium_12dp_black(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = black,
        fontFamily = poppins_regular,
    )
}

@Composable
fun Text_Poppins_Medium_16dp_black(text:String){
    Text(text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = black,
        fontFamily = poppins_regular,
    )
}

@Composable
fun Text_Poppins_Medium_12dp_LightGray(text:String){
    Text(text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = lightGray,
        fontFamily = poppins_regular
    )
}

@Composable
fun Action_Text(text : String,color: Color){
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = color,
        elevation = 0.dp,
        modifier = Modifier
            .padding(4.dp, 0.dp, 0.dp, 0.dp),
        content = {
            Box(modifier = Modifier.padding(8.dp, 1.5.dp, 8.dp, 0.dp)) {
                Text_Poppins_Regular_10dp_white("Completed")
            }
        }
    )
}


