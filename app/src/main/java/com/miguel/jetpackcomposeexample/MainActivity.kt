package com.miguel.jetpackcomposeexample

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.miguel.jetpackcomposeexample.ui.theme.JetPackComposeExampleTheme
import com.miguel.jetpackcomposeexample.ui.theme.md_theme_dark_error
import com.miguel.jetpackcomposeexample.ui.theme.md_theme_dark_onSecondary


class MainActivity : ComponentActivity() {
    lateinit var list: List<Message>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = listOf(
            Message("Slash", "Que onda mi pana :v","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSQ61jjF4Str6rNdLh4lzoKLK9Rty-k-_F92eqUaMLsMvLzuM9iAkb_89a2ImScnENk4Obxw9ArNobm0TmGV-6YMsJBG72FKb06JMN9Dg"),
            Message("Dave Mustaine", "Peda o que perrin :V?","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTzVFJKWQH5ITbtrCHmpP3ZuSDNWuPh2uvi0IOsXRPAr_YMDXmgwRKxKli3fh7sovkBo8kqRY5Hvm0xgOn5TyQo0lyp3e4Nt9PoKQKphZ0"),
            Message("Iori Yagami", "Vamos a darnos en la madre :v","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgGSAIJgddpq9Yw8hlEcj6gP0nqfqDRkeGEgjglF97LA&s"),
            Message("Laura Guldemond","Hola papi 7u7","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzfUSXNG4evLwQM3p84NBXdFanhXXbVzAk71O3wu_70A&s")
        )
        setContent {
            JetPackComposeExampleTheme {
 //               Column {
//                    Surface {
//                        Conversation(list)
//                        Spacer(modifier = Modifier.weight(1f))
//                        BottomNavigationBar()
//                    }
//                }
                Scaffold(
                    bottomBar = { BottomNavigationBar() }
                ) { innerPadding->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                       Conversation(list)
                    }
                }
            }
        }
    }
}
data class Message(val author: String, val body: String, val url: String)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageCard(message: Message){
    //acomoda componentes de forma horizontal
    // We keep track if the message is expanded or not in this
    // variable
    var isExpanded by remember { mutableStateOf(false)}
    Row(
        Modifier
            .clickable { isExpanded = !isExpanded }
            .padding(10.dp)) {
        AsyncImage(model = message.url, contentDescription = "urlimage",
            modifier = Modifier
                .size(60.dp)
                .padding(5.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape))
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )
        // acomoda componentes en forma vertical
        Column(modifier = Modifier
            .padding(5.dp)
        ) {
            Text(
                modifier = Modifier.padding(0.dp,0.dp,0.dp,0.dp),
                text = message.author,
                style = MaterialTheme.typography.titleMedium
            )
            //Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message.body,
                modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp),
                // If the message is expanded, we display all its content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ){
            Text(
                text = "2:00 am",
                modifier = Modifier.padding(5.dp),
                // If the message is expanded, we display all its content
                // otherwise we only display the first line
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Badge(
                modifier = Modifier.padding(10.dp),
                content = { Text("666") },
            )
        }
    }
}

@Composable
fun BottomNavigationBar(){
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Chats", "Novedades", "Comunidades", "Llamadas")
    val icons = listOf(
        Icons.Filled.Favorite,
        Icons.Filled.Favorite,
        Icons.Filled.Favorite,
        Icons.Filled.Favorite
    )
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun toobar(){
    val textStle = androidx.compose.ui.text.TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
    Row (Modifier.fillMaxWidth(1f)){
        Text(
            modifier = Modifier
                .padding(10.dp,5.dp,5.dp,5.dp),
            text = "Wahssap",
            color = MaterialTheme.colorScheme.secondary,
            style = textStle
        )
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.End
        ){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "Photo"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_search_24),
                    contentDescription = "Search"
                )
            }
            DropDownMenu()
        }
    }

    Divider(
       modifier= Modifier.padding(5.dp)
    )
}

/**
 * Compontente de mensajes
 * Este mensaje recibe un array de datos e pinta una lista de datos
 * */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Conversation(messages: List<Message>){
    Column {
        toobar()
        LazyColumn(Modifier.fillMaxHeight()) {
            items(messages){
                MessageCard(message = it)
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun DropDownMenu(){
    var expanded by remember { mutableStateOf(false) }
    val contextForToast = LocalContext.current.applicationContext
        Box {
            IconButton(onClick = { expanded = true}) {
                Icon(Icons.Default.MoreVert , contentDescription = "Open Menu")
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(text = {
                        Text("Nuevo grupo")
                    },
                        onClick = {
                            Toast.makeText(contextForToast, "¡Suscrito😎!", Toast.LENGTH_SHORT).show()
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = null,
                                tint = androidx.compose.ui.graphics.Color.Red
                            )
                        })
                    DropdownMenuItem(text = {
                        Text("Nueva difusión")
                    },
                        onClick = {
                            Toast.makeText(contextForToast, "¡Suscrito😎!", Toast.LENGTH_SHORT).show()
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Favorite,
                                contentDescription = null,
                                tint = androidx.compose.ui.graphics.Color.Red
                            )
                        })
                }
            }
        }
}


/*
* Previzualizacion de la info en el IDE
*
* */
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewMessageCard(){
    val list = listOf(
        Message("Slash", "Que onda mi pana :v","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSQ61jjF4Str6rNdLh4lzoKLK9Rty-k-_F92eqUaMLsMvLzuM9iAkb_89a2ImScnENk4Obxw9ArNobm0TmGV-6YMsJBG72FKb06JMN9Dg"),
        Message("Dave Mustaine", "Perrin se va armar la peda o que?","https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTzVFJKWQH5ITbtrCHmpP3ZuSDNWuPh2uvi0IOsXRPAr_YMDXmgwRKxKli3fh7sovkBo8kqRY5Hvm0xgOn5TyQo0lyp3e4Nt9PoKQKphZ0"),
        Message("Iori Yagami", "Jalate :v, van a llover vergazos ahorita","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgGSAIJgddpq9Yw8hlEcj6gP0nqfqDRkeGEgjglF97LA&s"),
        Message("Laura Guldemond","Hola papi 7u7","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzfUSXNG4evLwQM3p84NBXdFanhXXbVzAk71O3wu_70A&s")

    )
    JetPackComposeExampleTheme {
//        Column {
//            Surface {
//                Conversation(list)
//                Spacer(modifier = Modifier.weight(1f))
//                BottomNavigationBar()
//            }
//        }
        Scaffold(
            bottomBar = { BottomNavigationBar() }
        ) { innerPadding->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Conversation(list)
            }
        }
    }
}