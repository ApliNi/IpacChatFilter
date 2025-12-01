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
# IpacChatFilter.filter - Enable message filtering, default is true
# IpacChatFilter.bypass - Bypass message filtering, default is false

# 文件说明
# word_allow.txt - List of allowed words, one per line, used to handle false detections
# word_deny.txt  - List of sensitive words, one per line

# Replace player messages, only detect after closing, used to test misjudgment situations
setMessage: true

# Print logs when sensitive words are detected
log: true

# Sensitive words are replaced with (corresponding to each character)
wordReplaceTo: '*'

# Prefix replacement
preRegex:
  # Remove zero-width characters
  - regex: '[\u200B-\u200D\uFEFF]+'
    to: ''

# sensitive-word Module configuration
# https://github.com/houbb/sensitive-word
bsConfig:
  # Ignore case
  ignoreCase: true
  # Ignore full width and half width
  ignoreWidth: true
  # Ignore the way numbers are written
  ignoreNumStyle: true
  # Ignore traditional and simplified Chinese
  ignoreChineseStyle: true
  # ignore english style
  ignoreEnglishStyle: true
  # ignore repeated words
  ignoreRepeat: false
  # Whether to enable digital detection
  enableNumCheck: false
  # Whether to enable email detection
  enableEmailCheck: false
  # Whether to enable URL detection
  enableUrlCheck: false
  # Whether to enable IPv4 address detection
  enableIpv4Check: false
  # Whether to enable word detection
  enableWordCheck: true
  # If a sensitive word is found, it will be returned immediately without further matching.
  wordFailFast: false
  # Number check length
  numCheckLen: 8

# Character ignore list, used to ignore interfering characters between keywords
ignoreChars:
  # English symbols
  - '''`-=~!@#$%^&*()_+[]{}\|;:",./<>?'
  # White space characters
  - ' 	'
  # Chinese symbols
  - '，。、：；？！“”‘’『』「」【】《》〈〉〔〕（）【】！？，。：；·…'
  # Chinese characters
  - '一─—⸺～丨亅丶ˊˋˇˉ〇口'
  # letters, numbers
#  - '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
#  - '𝟶𝟷𝟸𝟹𝟺𝟻𝟼𝟽𝟾𝟿𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉'
#  - '𝟢𝟣𝟤𝟥𝟦𝟧𝟨𝟩𝟪𝟫𝖺𝖻𝖼𝖽𝖾𝖿𝗀𝗁𝗂𝗃𝗄𝗅𝗆𝗇𝗈𝗉𝗊𝗋𝗌𝗍𝗎𝗏𝗐𝗑𝗒𝗓𝖠𝖡𝖢𝖣𝖤𝖥𝖦𝖧𝖨𝖩𝖪𝖫𝖬𝖭𝖮𝖯𝖰𝖱𝖲𝖳𝖴𝖵𝖶𝖷𝖸𝖹'
#  - '𝟬𝟭𝟮𝟯𝟰𝟱𝟲𝟳𝟴𝟵𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭'
#  - '𝟘𝟙𝟚𝟛𝟜𝟝𝟞𝟟𝟠𝟡𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ'
#  - '𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡'
#  - '𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕'
#  - '𝐚𝐛𝐜𝐝𝐞𝐟𝐠𝐡𝐢𝐣𝐤𝐥𝐦𝐧𝐨𝐩𝐪𝐫𝐬𝐭𝐮𝐯𝐰𝐱𝐲𝐳𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏𝐐𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙'
#  - '𝑎𝑏𝑐𝑑𝑒𝑓𝑔𝑕𝑖𝑗𝑘𝑙𝑚𝑛𝑜𝑝𝑞𝑟𝑠𝑡𝑢𝑣𝑤𝑥𝑦𝑧𝐴𝐵𝐶𝐷𝐸𝐹𝐺𝐻𝐼𝐽𝐾𝐿𝑀𝑁𝑂𝑃𝑄𝑅𝑆𝑇𝑈𝑉𝑊𝑋𝑌𝑍'
#  - '𝒂𝒃𝒄𝒅𝒆𝒇𝒈𝒉𝒊𝒋𝒌𝒍𝒎𝒏𝒐𝒑𝒒𝒓𝒔𝒕𝒖𝒗𝒘𝒙𝒚𝒛𝑨𝑩𝑪𝑫𝑬𝑭𝑮𝑯𝑰𝑱𝑲𝑳𝑴𝑵𝑶𝑷𝑸𝑹𝑺𝑻𝑼𝑽𝑾𝑿𝒀𝒁'
#  - 'ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ'
#  - 'аbсԁеfցһіјkӏmոорԛrѕtսvԝхуzАВСDЕFGНІЈКLМNОРԚRЅΤՍVԜХΥΖ'
#  - '𝓪𝓫𝓬𝓭𝓮𝓯𝓰𝓱𝓲𝓳𝓴𝓵𝓶𝓷𝓸𝓹𝓺𝓻𝓼𝓽𝓾𝓿𝔀𝔁𝔂𝔃𝓐𝓑𝓒𝓓𝓔𝓕𝓖𝓗𝓘𝓙𝓚𝓛𝓜𝓝𝓞𝓟𝓠𝓡𝓢𝓣𝓤𝓥𝓦𝓧𝓨𝓩'
#  - '𝒶𝒷𝒸𝒹𝑒𝒻𝑔𝒽𝒾𝒿𝓀𝓁𝓂𝓃𝑜𝓅𝓆𝓇𝓈𝓉𝓊𝓋𝓌𝓍𝓎𝓏𝒜𝐵𝒞𝒟𝐸𝐹𝒢𝐻𝐼𝒥𝒦𝐿𝑀𝒩𝒪𝒫𝒬𝑅𝒮𝒯𝒰𝒱𝒲𝒳𝒴𝒵'

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