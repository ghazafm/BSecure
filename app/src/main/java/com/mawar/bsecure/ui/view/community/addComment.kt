//package com.mawar.bsecure.ui.view.community
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.mawar.bsecure.R
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun addComment(post: Post, onBackClick: () -> Unit) {
//    var replyText by remember { mutableStateOf("") } // State untuk teks balasan
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(Color(0xFF5A2D82))
//                .height(100.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                IconButton(onClick = {onBackClick()}) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
//                        contentDescription = "Back",
//                        tint = Color.White,
//                        modifier = Modifier.size(30.dp)
//                    )
//                }
//
//                Spacer(modifier = Modifier.weight(0.65f))
//
//                Text(
//                    text = "Komunitas",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.White,
//                    textAlign = TextAlign.Center
//                )
//
//                Spacer(modifier = Modifier.weight(1f))
//            }
//        }
//        // Post Content
//        Column(
//            modifier = Modifier
//                .weight(1f) // Mengambil ruang sisa
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Profile Picture
//                Icon(
//                    painter = painterResource(id = post.profilePic),
//                    contentDescription = post.username,
//                    modifier = Modifier
//                        .size(50.dp)
//                        .clip(CircleShape) // Membuat gambar berbentuk lingkaran
//                        .background(Color.Gray) // Fallback jika gambar gagal dimuat
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                // Username and Handle
//                Column {
//                    Text(
//                        text = post.username,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 16.sp
//                    )
//                    Text(
//                        text = post.handle,
//                        color = Color.Gray,
//                        fontSize = 14.sp
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Post Content
//            Text(
//                text = post.content,
//                fontSize = 14.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Post Time
//            Text(
//                text = post.time,
//                fontSize = 12.sp,
//                color = Color.Gray
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
////            // Display Reply
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {}}
////                Icon(
////                    painter = painterResource(id = post.profilePic),
////                    contentDescription = post.username,
////                    modifier = Modifier
////                        .size(40.dp)
////                        .clip(CircleShape)
////                        .background(Color.Gray)
////                )
////                Spacer(modifier = Modifier.width(8.dp))
//////                Column {
//////                    Text(
//////                        text = "${post.username} ${post.handle} | 28s",
//////                        fontSize = 14.sp,
//////                        color = Color.Gray
//////                    )
//////                    Text(text = "Posting balasan Anda", fontSize = 14.sp)
//////                }
////            }
////        }
//
//        // Reply Input
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//                .background(Color.White),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TextField(
//                value = replyText,
//                onValueChange = { replyText = it },
//                placeholder = { Text("Tuliskan balasan...") },
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(end = 8.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color(0xFFF0F0F0),
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent
//                )
//            )
//            IconButton(
//                onClick = {
//                    // Tambahkan logika untuk mengirim balasan
//                    replyText = "" // Hapus teks setelah mengirim
//                }
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_email_24),
//                    modifier = Modifier
//                        .size(40.dp)
//                        .background(Color.Transparent),
//                    contentDescription = "Send"
//
//                )
//            }
//        }
//    }
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCommentScreen() {
//    val dummyPost = Post(
//        id = "1",
//        profilePic = R.drawable.baseline_email_24,
//        username = "John Doe",
//        handle = "@johndoe",
//        content = "This is a sample post content for preview purposes. It demonstrates how the post will look in the comment screen.",
//        time = "2h"
//    )
//
//    addComment(
//        post = dummyPost,
//        onBackClick = {}
//    )
//}