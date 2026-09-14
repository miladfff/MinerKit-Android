# MinerKit — سند زنده پروژه و نقطه ادامه

آخرین به‌روزرسانی: ۱۴ سپتامبر ۲۰۲۶

## وضعیت فعلی

- Repository رسمی Android: `miladfff/MinerKit-Android`
- Branch اصلی: `main`
- Application ID فعلی: `com.minerkit.android`
- نسخه فعلی: `1.0.0-alpha01`
- GitHub Actions فعال و Build شماره ۳ موفق است.
- APK آزمایشی قابل نصب با نام `MinerKit-v1.0.0-alpha01.apk` ساخته شد.
- این نسخه یک Alpha رابط کاربری با داده‌های نمایشی است؛ اتصال واقعی LAN و Backend هنوز انجام نشده است.

## امکانات موجود در Alpha

- رابط فارسی و RTL با تم تیره MinerKit
- ناوبری پایین: خانه، ماینرها، ابزارها، بازار، حساب
- داشبورد فارم و شاخص‌های هش‌ریت، مصرف، وضعیت و هشدار
- صفحه اسکن LAN و فهرست ماینرها با داده نمایشی
- جزئیات ماینر، استخر، لاگ، Restart و تشخیص هوشمند نمایشی
- حالت ساده و حالت تکنسین
- ابزارهای AI، محاسبه سود، Firmware و اتوماسیون
- صفحات بازار، حساب و اشتراک
- وضعیت Unknown جدا از Healthy
- Build خودکار APK پس از هر Push روی `main`

## تصمیم‌های قطعی محصول

- MinerKit یک پلتفرم مدیریت ASIC مبتنی بر قابلیت دستگاه است.
- Android به‌صورت Local-first کار می‌کند؛ اسکن و کنترل LAN بدون ورود و بدون Cloud نیز باید فعال بماند.
- حساب برای Cloud، AI، کنترل از راه دور و اشتراک لازم است.
- Backend مرجع نهایی حساب، اشتراک، تاریخچه ابری، AI، بازار، Firmware، هشدار و سرویس‌های Remote است.
- اولویت v1.0 با WhatsMiner و Antminer است.
- کنترل دستگاه با Universal Miner Adapter پیاده‌سازی می‌شود.
- پیچیدگی تدریجی است: Simple پیش‌فرض و Technician برای اطلاعات و عملیات تخصصی.
- Scan داخل بخش Miners قرار می‌گیرد.
- عملیات گروهی قبل از اجرا باید دستگاه‌های تحت تأثیر، موارد پشتیبانی‌نشده و ریسک را نمایش دهد.
- AI در رابط با نام «AI MinerKit» نمایش داده می‌شود، نه نام ارائه‌دهنده مدل.
- تغییرات جدید خارج از محدوده v1.0 باید وارد Backlog نسخه‌دار شوند.

## ترتیب اجرای v1.0

1. Foundation و Design System
2. LAN Discovery واقعی
3. Universal Miner Adapter
4. Dashboard و Miners واقعی
5. جزئیات و کنترل دستگاه
6. Logs و AI Diagnosis
7. Profitability و Market
8. Account و Subscription
9. Alerts و History
10. QA، امضای Release و انتشار

## اشتراک و پرداخت

- نمایش فعلی بین‌المللی: Pro برابر 9.99 دلار در ماه و Farm برابر 29.99 دلار در ماه.
- انتخاب ماهانه/سالانه با ۲۰٪ صرفه‌جویی سالانه در طراحی تأییدشده وجود دارد.
- ایران باید قیمت ارزان‌تر و پرداخت مجاز تومانی داشته باشد.
- نتیجه نهایی پرداخت و Entitlement همیشه در MinerKit Backend ثبت می‌شود.
- قیمت‌ها قبل از عرضه عمومی باید دوباره تأیید شوند.

## Backend و Decamond

- دامنه معرفی‌شده: `https://decamond.net`
- سایت فعال است و ورود/ساخت حساب Google دارد.
- Swagger، OpenAPI یا API عمومی MinerKit روی دامنه پیدا نشد.
- هیچ Repository سرور Decamond در اتصال فعلی GitHub مشاهده نشد.
- پیشنهاد فعلی در صورت نبود Backend آماده: ساخت API نسخه‌دار MinerKit روی `https://api.decamond.net`.
- حوزه‌های API: Auth، Miners/Farms، Diagnostics، Firmware، Subscriptions، Profitability، Market، Pools و Alerts.
- الزامات امنیتی: HTTPS، Access Token کوتاه‌مدت، Refresh Token، حفاظت از Credential ماینر، Rate Limit و Audit Log.
- Backend نباید مستقیم به IP خصوصی ماینرها وصل شود؛ Android Local Controller این کار را انجام می‌دهد و Gateway/Agent دائمی در آینده اضافه می‌شود.

## نقطه شروع جلسه بعد

1. تعیین اینکه Backend موجود است یا باید روی `api.decamond.net` ساخته شود.
2. در صورت موجود بودن: ارائه Repository یا Swagger/OpenAPI و حساب تست.
3. در صورت ساخت جدید: ایجاد Repository سرور و قرارداد OpenAPI v1.
4. اضافه‌کردن Retrofit/OkHttp، مدیریت Token و Environment امن به Android.
5. پیاده‌سازی LAN Discovery واقعی و Adapter اولیه WhatsMiner M50/M30.
6. تست APK روی گوشی واقعی و ثبت ایرادهای رابط.

## نکات Release

- APK فعلی Debug است و برای تست اولیه مناسب است.
- برای انتشار باید Keystore، امضای Release، نام/آیکن نهایی، Privacy Policy و بررسی امنیت شبکه تکمیل شود.
- Credential واقعی، رمز سرور یا کلید API نباید داخل GitHub commit شود.
