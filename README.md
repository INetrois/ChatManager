![title](.github/assets/banners/title.png)

> Powerful, flexible and simple chat manager for Minecraft servers with LuckPerms & Vault integration.

---

![features](.github/assets/banners/features.png)

- ⚡ **Prefix & suffix support (LuckPerms)**
- 🕵️ **Local & global chat**
- 🗄️ **Simple configuration**
- 📟 **Terminal logging**

---

![chat-preview](.github/assets/banners/chat-preview.png)

![chat-without-prefix-n-suffix](.github/assets/chat/chat-without-prefix-n-suffix.png)
> Chat preview without prefix & suffix

![chat-with-prefix](.github/assets/chat/chat-with-prefix.png)
> Chat preview with prefix

![chat-with-suffix](.github/assets/chat/chat-with-suffix.png)
> Chat preview with suffix

![chat-with-prefix-n-suffix](.github/assets/chat/chat-with-prefix-n-suffix.png)
> Chat preview with prefix & suffix

---

![commands-preview](.github/assets/banners/commands-preview.png)

![commands-chat-info](.github/assets/commands/chat-info.png)
> `/chat info` – this argument displays chat information

![commands-chat-reload](.github/assets/commands/config-reload.png)
> `/chat reload` – this argument reloads the configuration

![commands-chat-info](.github/assets/commands/not-enough-arguments.png)
> `/chat <no arguments>` – executed when there are not enough arguments

![commands-chat-info](.github/assets/commands/chat-info.png)
> `/chat <unknown argument>` – executed when an unknown argument is entered

---

![default-configuration](.github/assets/banners/default-configuration.png)

```yaml
chat:
  channels:
    local:
      distance: 250
      symbol: "ʟ"
      color: "&a"

    global:
      prefix: "!"
      symbol: "ɢ"
      color: "&6"

  format:
    local:  "&7{symbol} &7| &f{prefix}&x&3&1&b&9&f&7{player}&f{suffix} &7» &x&C&E&F&F&B&8{message}"
    global: "&7{symbol} &7| &f{prefix}&x&3&1&b&9&f&7{player}&f{suffix} &7» &f{message}"
```

---

![requirements](.github/assets/banners/requirements.png)
