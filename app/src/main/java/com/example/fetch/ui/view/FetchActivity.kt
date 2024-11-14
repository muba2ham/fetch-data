package com.example.fetch.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.fetch.R
import com.example.fetch.api.model.FetchDataObject
import com.example.fetch.ui.theme.FetchTheme
import com.example.fetch.ui.viewmodel.FetchActivityViewModel

class FetchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->

                    val fetchDataViewModel = viewModels<FetchActivityViewModel>()
                    FetchDataMap(fetchDataViewModel)

                }
            }
        }
    }

}

@Composable
fun ListItem(id: Long,  name: String) {

    val _id = stringResource(R.string.item_id) + id
    val _name = stringResource(R.string.item_name) + name

    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.tertiaryContainer)
        .padding(dimensionResource(R.dimen.section_padding))
        .fillMaxWidth()) {
        Text(
            text = _id,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.section_padding))
        )
        Text(
            text = _name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.section_padding))
        )
    }

}

@Composable
fun Header(title: String, isExpanded: Boolean, onHeaderClicked: () -> Unit) {

    val icon = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown

    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(dimensionResource(R.dimen.title_padding))
        .clickable { onHeaderClicked() }
        .padding(dimensionResource(R.dimen.section_padding))) {
        Column(Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
        }
        Image(
            imageVector = icon,
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size))
        )
    }

}

@Composable
fun FetchDataMap(viewModel: Lazy<FetchActivityViewModel>) {

    val sectionsData = remember { viewModel.value.networkData }
    val isExpandedMap = remember { viewModel.value.isExpandedMap }

    LazyColumn(
        content = {
            sectionsData.onEachIndexed { index, entry ->
                section(
                    contentTitle = entry.key,
                    contentList = entry.value,
                    isExpanded = isExpandedMap[index] ?: false,
                    onHeaderClick = {
                        isExpandedMap[index] = !(isExpandedMap[index] ?: false)
                    }
                )
            }
        }
    )

}

fun LazyListScope.section(
    contentTitle : Long,
    contentList: List<FetchDataObject>,
    isExpanded: Boolean,
    onHeaderClick: () -> Unit
) {

    item {
        Header(
            title = stringResource(R.string.list_id) + contentTitle.toString(),
            isExpanded = isExpanded,
            onHeaderClicked = onHeaderClick
        )
    }

    if(isExpanded) {
        items(contentList) {
            ListItem(it.id, it.name!!)
        }
    }

}

