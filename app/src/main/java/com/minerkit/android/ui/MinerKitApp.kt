package com.minerkit.android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.minerkit.android.model.*

enum class Tab(val fa: String) { HOME("خانه"), MINERS("ماینرها"), TOOLS("ابزارها"), MARKET("بازار"), ACCOUNT("حساب") }

@Composable fun MinerKitApp() = MinerKitTheme {
    var tab by remember { mutableStateOf(Tab.HOME) }
    var selected by remember { mutableStateOf<Miner?>(null) }
    var technician by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { Header(technician) { technician = !technician } },
        bottomBar = { NavigationBar(containerColor = Surface) { Tab.entries.forEach { item ->
            NavigationBarItem(selected = tab == item, onClick = { selected = null; tab = item },
                icon = { Icon(tabIcon(item), item.fa) }, label = { Text(item.fa) })
        } } }
    ) { padding -> Box(Modifier.padding(padding).fillMaxSize().background(Ink)) {
        if (selected != null) MinerDetails(selected!!, technician) { selected = null }
        else when(tab) {
            Tab.HOME -> Dashboard { tab = Tab.MINERS }
            Tab.MINERS -> MinersScreen { selected = it }
            Tab.TOOLS -> ToolsScreen(technician)
            Tab.MARKET -> MarketScreen()
            Tab.ACCOUNT -> AccountScreen()
        }
    } }
}

@Composable private fun Header(technician: Boolean, toggle: () -> Unit) {
    TopAppBar(title = { Column { Text("MinerKit", fontWeight = FontWeight.ExtraBold); Text(if (technician) "حالت تکنسین" else "حالت ساده", fontSize = 11.sp, color = Emerald) } },
        actions = { FilterChip(selected = technician, onClick = toggle, label = { Text("TECH") }); Spacer(Modifier.width(12.dp)) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Ink))
}

private fun tabIcon(tab: Tab) = when(tab) { Tab.HOME -> Icons.Default.Home; Tab.MINERS -> Icons.Default.Memory; Tab.TOOLS -> Icons.Default.Build; Tab.MARKET -> Icons.Default.ShowChart; Tab.ACCOUNT -> Icons.Default.Person }

@Composable private fun Dashboard(openMiners: () -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("وضعیت فارم", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
        item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Metric("هش‌ریت کل", "250.5 TH/s", Modifier.weight(1f)); Metric("مصرف", "6.31 kW", Modifier.weight(1f))
        } }
        item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Metric("آنلاین", "2 از 4", Modifier.weight(1f)); Metric("هشدار", "1", Modifier.weight(1f), Amber)
        } }
        item { StatusCard("نیاز به بررسی", "Miner 02 دمای بالا و افت هش‌ریت دارد.", Amber, Icons.Default.Warning) }
        item { StatusCard("کنترل محلی فعال", "در زمان قطعی اینترنت، کنترل LAN ادامه دارد.", Emerald, Icons.Default.Wifi) }
        item { Button(openMiners, Modifier.fillMaxWidth().height(52.dp)) { Icon(Icons.Default.Memory, null); Spacer(Modifier.width(8.dp)); Text("مشاهده ماینرها") } }
    }
}

@Composable private fun Metric(label: String, value: String, modifier: Modifier, color: Color = Emerald) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Surface)) { Column(Modifier.padding(16.dp)) { Text(label, color = Color.Gray); Spacer(Modifier.height(6.dp)); Text(value, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold) } }
}

@Composable private fun StatusCard(title: String, body: String, color: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Card(colors = CardDefaults.cardColors(containerColor = Surface)) { Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = color); Spacer(Modifier.width(12.dp)); Column { Text(title, fontWeight = FontWeight.Bold); Text(body, color = Color.LightGray, fontSize = 13.sp) } } }
}

@Composable private fun MinersScreen(open: (Miner) -> Unit) {
    var scanning by remember { mutableStateOf(false) }
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text("ماینرهای شبکه", fontSize = 23.sp, fontWeight = FontWeight.Bold); Button(onClick = { scanning = !scanning }) { Icon(Icons.Default.Radar, null); Text(if(scanning) " توقف" else " اسکن LAN") } } }
        if(scanning) item { LinearProgressIndicator(Modifier.fillMaxWidth()) }
        items(demoMiners) { miner -> Card(onClick = { open(miner) }, colors = CardDefaults.cardColors(containerColor = Surface)) { Row(Modifier.fillMaxWidth().padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(10.dp).background(stateColor(miner.state), RoundedCornerShape(50))); Spacer(Modifier.width(12.dp)); Column(Modifier.weight(1f)) { Text(miner.name, fontWeight = FontWeight.Bold); Text("${miner.model} · ${miner.ip}", color = Color.Gray, fontSize = 12.sp) }; Column(horizontalAlignment = Alignment.End) { Text("${miner.hashrate} TH/s", color = Emerald); Text("${miner.temperature}°C", color = if(miner.temperature > 75) Amber else Color.Gray) }
        } } }
    }
}

private fun stateColor(s: MinerState) = when(s) { MinerState.HEALTHY -> Emerald; MinerState.WARNING -> Amber; MinerState.OFFLINE -> Danger; MinerState.UNKNOWN -> Color.Gray }

@Composable private fun MinerDetails(m: Miner, technician: Boolean, back: () -> Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { TextButton(back) { Icon(Icons.Default.ArrowBack, null); Text(" بازگشت") } }
        item { Text(m.name, fontSize = 26.sp, fontWeight = FontWeight.Bold); Text("${m.model} · ${m.ip}", color = Color.Gray) }
        item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { Metric("هش‌ریت", "${m.hashrate} TH/s", Modifier.weight(1f)); Metric("دما", "${m.temperature}°C", Modifier.weight(1f), if(m.temperature > 75) Amber else Emerald) } }
        item { Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) { Metric("توان", "${m.powerWatts} W", Modifier.weight(1f)); Metric("استخر", m.pool, Modifier.weight(1f)) } }
        item { StatusCard("تشخیص هوشمند", if(m.state == MinerState.WARNING) "احتمال مشکل خنک‌سازی؛ اطمینان 86٪" else "مشکل قطعی شناسایی نشده است.", if(m.state == MinerState.WARNING) Amber else Emerald, Icons.Default.AutoAwesome) }
        item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { OutlinedButton({}, Modifier.weight(1f)) { Text("استخرها") }; OutlinedButton({}, Modifier.weight(1f)) { Text("لاگ‌ها") }; Button({}, Modifier.weight(1f)) { Text("Restart") } } }
        if(technician) item { Card(colors = CardDefaults.cardColors(containerColor = Surface)) { Column(Modifier.padding(16.dp)) { Text("اطلاعات تکنسین", fontWeight = FontWeight.Bold); Text("Stable ratio: ${if(m.expectedHashrate > 0) (m.hashrate / m.expectedHashrate * 100).toInt() else 0}%\nFirmware: API pending\nHashboards: capability check required", color = Color.LightGray) } } }
    }
}

@Composable private fun ToolsScreen(technician: Boolean) { LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
    item { Text("ابزارها", fontSize = 24.sp, fontWeight = FontWeight.Bold) }
    item { ToolCard("تشخیص AI", "تحلیل لاگ، علت‌های احتمالی و راهنمای تعمیر", Icons.Default.AutoAwesome) }
    item { ToolCard("محاسبه سود", "درآمد، برق، کارمزد استخر و ROI", Icons.Default.Calculate) }
    item { ToolCard("Firmware", if(technician) "تطبیق نسخه، ریسک و ارتقای امن" else "بررسی به‌روزرسانی امن", Icons.Default.SystemUpdate) }
    item { ToolCard("اتوماسیون", "هشدار دما، افت هش‌ریت و آفلاین شدن", Icons.Default.NotificationsActive) }
} }

@Composable private fun ToolCard(title: String, body: String, icon: androidx.compose.ui.graphics.vector.ImageVector) { Card(colors = CardDefaults.cardColors(containerColor = Surface)) { Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment = Alignment.CenterVertically) { Icon(icon, null, tint = Emerald, modifier = Modifier.size(30.dp)); Spacer(Modifier.width(14.dp)); Column { Text(title, fontWeight = FontWeight.Bold); Text(body, color = Color.Gray, fontSize = 13.sp) } } } }

@Composable private fun MarketScreen() { LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { item { Text("بازار", fontSize = 24.sp, fontWeight = FontWeight.Bold) }; item { Metric("Bitcoin", "$64,820", Modifier.fillMaxWidth()) }; item { Metric("درآمد شبکه", "$0.052 / TH/day", Modifier.fillMaxWidth(), Amber) }; item { Text("داده‌های زنده پس از اتصال MinerKit Backend نمایش داده می‌شوند.", color = Color.Gray) } } }

@Composable private fun AccountScreen() { LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) { item { Text("حساب و اشتراک", fontSize = 24.sp, fontWeight = FontWeight.Bold) }; item { StatusCard("بدون ورود", "اسکن و کنترل LAN رایگان و فعال است.", Emerald, Icons.Default.Lan) }; item { ToolCard("Pro", "تشخیص AI، تاریخچه ابری و کنترل از راه دور", Icons.Default.Star) }; item { ToolCard("Farm", "چند فارم، تیم، گزارش و اتوماسیون پیشرفته", Icons.Default.Factory) }; item { Text("پرداخت ایران: تومان · پرداخت بین‌المللی: ماهانه/سالانه", color = Color.Gray) } } }
