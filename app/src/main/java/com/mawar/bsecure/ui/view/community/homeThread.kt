package com.mawar.bsecure.ui.view.community

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mawar.bsecure.R

data class Post(val username: String, val handle: String, val content: String, val time: String, val profilePic: Int)

val samplePosts = listOf(
    Post("Lukas Awan", "@LukasAwan", "Hello guys! nama saya lukas awan saya berkuliah di Universitas Brawijaya!", "21:45", R.drawable.user),
    Post("Kalkun", "@HaikalGibran", "Eiy, kalian udah coba fitur fitur nya belum?", "21:45", R.drawable.user),
    Post("Aril", "@AzrilJanuar", "Waaw, aku baru download aplikasi ini, keren banget aplikasinya!", "21:45", R.drawable.user)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(onPostClick: (Post) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF5A2D82))
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Handle back button click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(0.65f))

                Text(
                    text = "Komunitas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // Post list
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(samplePosts) { post ->
                CommunityPostItem(post = post, onClick = { onPostClick(post) })
                HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CommunityPostItem(post: Post, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            painter = painterResource(id = post.profilePic),
            contentDescription = post.username,
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = post.username, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = post.handle, color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = post.content, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* Handle comment */ }) {
                    Icon(painter = painterResource(id = R.drawable.outline_comment_24), contentDescription = "Comment")
                }
                IconButton(onClick = { /* Handle like */ }) {
                    Icon(painter = painterResource(id = R.drawable.like), contentDescription = "Like")
                }
                IconButton(onClick = { /* Handle bookmark */ }) {
                    Icon(painter = painterResource(id = R.drawable.baseline_bookmark_border_24), contentDescription = "Bookmark")
                }
                IconButton(onClick = { /* Handle share */ }) {
                    Icon(painter = painterResource(id = R.drawable.outline_share_24), contentDescription = "Share")
                }
                Text(text = post.time, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}