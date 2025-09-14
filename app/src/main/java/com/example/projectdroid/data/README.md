Yes 👍 — the nc ... 4028 interface is the cgminer API. You can send different commands in JSON instead of just {"command":"summary"}.

Here are the most useful ones:

🔹 Common cgminer API commands

summary → returns overall stats (what you already did).

devs → per-device stats (hashrate, temp, fan, HW errors, etc).

pools → info about configured pools, their status, accepted/rejected shares.

stats → detailed stats including temperature, voltage, frequency, etc.

devdetails → detailed ASIC/board information.

version → cgminer version.

config → current miner configuration.

devstatus → ASIC-chip status (errors, timings, voltages).

coin → coin being mined, difficulty, etc.

🔹 Control / management commands

⚠️ Some are powerful — be careful:

restart → restart cgminer.

quit → quit cgminer.

switchpool → switch to another pool (needs pool ID).

enable / disable → enable/disable a device.

notify → send a notification.

save → save settings to config file.


echo '{"command":"devs"}' | nc 192.168.1.172 4028
echo '{"command":"pools"}' | nc 192.168.1.172 4028
echo '{"command":"stats"}' | nc 192.168.1.172 4028
echo '{"command":"version"}' | nc 192.168.1.172 4028

echo '{"command":"summary"}' | nc 192.168.1.172 4028
echo '{"command":"summary"}' | nc 192.168.1.131 4028
echo '{"command":"stats"}' | nc 192.168.1.131 4028
echo '{"command":"sedevs}' | nc 192.168.1.131 4028

ssh root@192.168.1.172 "halt"
ssh root@192.168.1.172 "poweroff"
ssh root@192.168.1.172 "reboot"
root
