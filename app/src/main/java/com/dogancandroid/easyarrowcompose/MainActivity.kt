package com.dogancandroid.easyarrowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.dogancandroid.easyarrowcompose.ui.Item
import com.dogancandroid.easyarrowcompose.ui.MainViewModel
import com.dogancandroid.easyarrowcompose.ui.theme.EasyArrowComposeTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyArrowComposeTheme {
                val scrollState = rememberLazyListState()
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        EasyArrow(
                            lazyListState = scrollState,
                            arrowImage = painterResource(id = R.drawable.ic_arrow),
                            Color.Black
                        )
                    }
                ) { paddingValues ->

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    ) {
                        itemsIndexed(viewModel.itemList) { index, item ->
                            ItemCard(
                                item, onClick = {


                                }

                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun ItemCard(item: Item, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp, top = 10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        val constrains = ConstraintSet {
            val imageRef = createRefFor("image")
            val nameRef = createRefFor("name")

            constrain(imageRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }

            constrain(nameRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(imageRef.end)
                end.linkTo(parent.end)
            }

        }

        ConstraintLayout(
            constrains,
            Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(end = 30.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bag),
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
                    .padding(20.dp)
                    .layoutId("image"),
                contentScale = ContentScale.Fit,
            )
            Text(

                modifier = Modifier.layoutId("name"),
                text = item.name,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,

            )


        }


    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EasyArrowComposeTheme {
        Greeting("Android")
    }
}