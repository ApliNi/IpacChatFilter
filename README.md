# IpacChatFilter
基于 houbb/sensitive-word 的 Minecraft 敏感词处理插件

下载: https://modrinth.com/plugin/ipacchatfilter

高级敏感词替换插件, 基于 [sensitive-word](https://github.com/houbb/sensitive-word) 项目.
- 支持正则替换
- 前置处理 (移除零宽字符)
- 支持自定义关键词
- 支持排除关键词
- 支持忽略大小写和全角半角
- 支持忽略中文繁体字
- 内置 URL, IPv4 地址, 邮箱地址 检测
- 支持忽略干扰字符
- 支持权限控制

---

## 功能和指令
- `/icf`
    - `/icf reload`         - 重新加载配置

### 配置
```yaml

# 权限说明
# IpacChatFilter.filter - 启用消息过滤, 默认为 true
# IpacChatFilter.bypass - 绕过消息过滤, 默认为 false

# 文件说明
# word_allow.txt - 允许词列表, 每行一个, 用于处理误检的情况
# word_deny.txt  - 敏感词列表, 每行一个

# 前置替换
preRegex:
  # 移除零宽字符
  - regex: '[\u200B-\u200D\uFEFF]+'
    to: ''

# sensitive-word 模块配置
# https://github.com/houbb/sensitive-word
bsConfig:
  # 忽略大小写
  ignoreCase: true
  # 忽略全角半角
  ignoreWidth: true
  # 忽略数字的写法
  ignoreNumStyle: true
  # 忽略中文繁简体
  ignoreChineseStyle: true
  # 忽略英文样式
  ignoreEnglishStyle: true
  # 忽略重复词
  ignoreRepeat: false
  # 是否开启数字检测
  enableNumCheck: false
  # 是否开启邮箱检测
  enableEmailCheck: false
  # 是否开启URL检测
  enableUrlCheck: false
  # 是否开启IPv4地址检测
  enableIpv4Check: false
  # 是否开启单词检测
  enableWordCheck: true
  # 找到一个敏感词就立即返回，不再继续匹配
  wordFailFast: false
  # 数字检查长度
  numCheckLen: 8

# 字符忽略列表, 用于忽略关键词之间的干扰字符
ignoreChars:
  # 英文符号
  - '''`-=~!@#$%^&*()_+[]{}\|;:",./<>?'
  # 空白字符
  - ' 	'
  # 中文符号
  - '，。、：；？！“”‘’『』「」【】《》〈〉〔〕（）【】！？，。：；·…'
  # 中文字符
  - '一─—⸺～丨亅丶ˊˋˇˉ〇口'
  # 字母, 数字
  - '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

# 敏感词替换为 (对应每个字符)
wordReplaceTo: '*'

# 消息被替换时打印日志
log: true

```

### 权限
```yaml
permissions:
  IpacChatFilter.filter:
    description: 启用消息过滤
    default: true
  IpacChatFilter.bypass:
    description: 绕过消息过滤
    default: false
```

## 开源软件
- https://github.com/houbb/sensitive-word - 基于 DFA 算法实现的高性能 java 敏感词过滤工具框架