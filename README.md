# MinerKit Android

Local-first ASIC miner management for Android. The first implementation includes the approved Persian RTL dashboard, LAN miner list/scan experience, capability-aware device details, Simple/Technician modes, AI diagnosis surface, profitability tools, market, and subscription/account screens.

## Build

```bash
gradle assembleDebug
```

Every push to `main` builds an installable debug APK. Download it from the latest GitHub Actions run under **Artifacts → MinerKit-debug-apk**.

## Architecture baseline

- LAN scan/control remains usable without login or cloud availability.
- MinerKit Backend is the source of truth for auth, subscriptions, AI, market data, cloud history, firmware metadata, alerts, and remote services.
- WhatsMiner and Antminer are the v1 adapter priorities.
- Unknown device state is never presented as healthy.

Current UI uses safe demo data until the versioned backend contract and device credentials are supplied.
