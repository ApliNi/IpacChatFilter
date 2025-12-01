# IpacChatFilter
A Minecraft sensitive word processing plugin based on houbb/sensitive-word

Download: https://modrinth.com/plugin/ipacchatfilter

An advanced sensitive word replacement plugin, based on the sensitive-word [houbb/sensitive-word](https://github.com/houbb/sensitive-word) project.
- Supports regex replacement
- Pre-processing (removes zero-width characters)
- Supports custom keywords
- Supports exclusion keywords
- Supports ignoring case and full/half-width characters
- Supports ignoring traditional Chinese characters
- Built-in URL, IPv4 address, and email address detection
- Supports ignoring interfering characters
- Supports permission control

---

## Features and Commands
- `/icf`
  - `/icf reload` - Reloads the configuration

### Configuration
```yaml
# Permission description
# IpacChatFilter.filter - Enables message filtering, default is true
# IpacChatFilter.bypass - Bypasses message filtering, default is false

# File description
# word_allow.txt - Allow list, one word per line, used to handle false positives
# word_deny.txt - Deny list (sensitive words), one word per line

# Pre-replacement
preRegex:
  # Remove zero-width characters
  - regex: '[\u200B-\u200D\uFEFF]+'
    to: ''

# sensitive-word module configuration
# https://github.com/houbb/sensitive-word
bsConfig:
  # Ignore case
  ignoreCase: true
  # Ignore full/half-width characters
  ignoreWidth: true
  # Ignore number styles
  ignoreNumStyle: true
  # Ignore traditional/simplified Chinese
  ignoreChineseStyle: true
  # Ignore English styles
  ignoreEnglishStyle: true
  # Ignore repeated words
  ignoreRepeat: false
  # Enable number detection
  enableNumCheck: false
  # Enable email detection
  enableEmailCheck: false
  # Enable URL detection
  enableUrlCheck: false
  # Enable IPv4 address detection
  enableIpv4Check: false
  # Enable word detection
  enableWordCheck: true
  # Return immediately after finding one sensitive word, do not continue matching
  wordFailFast: false
  # Number check length
  numCheckLen: 8

# Character ignore list, used to ignore interfering characters between keywords
ignoreChars:
  # English symbols
  - '''`-=~!@#$%^&*()_+[]{}\|;:",./<>?'
  # Whitespace characters
  - ' 	'
  # Chinese symbols
  - '，。、：；？！“”‘’『』「」【】《》〈〉〔〕（）【】！？，。：；·…'
  # Chinese characters
  - '一─—⸺～丨亅丶ˊˋˇˉ〇口'
  # Letters, numbers
  - '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

# Replace sensitive words with (for each character)
wordReplaceTo: '*'

# Log when a message is replaced
log: true
```

### Permissions
```yaml
permissions:
  IpacChatFilter.filter:
    description: Enables message filtering
    default: true
  IpacChatFilter.bypass:
    description: Bypasses message filtering
    default: false
```

## Open Source Software
- https://github.com/houbb/sensitive-word - A high-performance Java sensitive word filtering tool framework based on the DFA algorithm