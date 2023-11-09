# 리팩터링 사례들

<!--Writerside adds this topic when you create a new documentation project.
You can use it as a sandbox to play with Writerside features, and remove it from the TOC when you don't need it anymore.-->

## 리팩터링 관련 정리들이 있는 문서들

- 지금은 문서들이 많이 산재해 있음
  - Notion
     - 개인
         - [Stdudy / Refactoring](https://www.notion.so/ctemplate/8c0744e433c44698888a3eca6ee9247d?v=cf502059be82427db14762fbb7d214fa&pvs=4)
             - ![img_1.png](img_1.png)
         - [Study / Refactoring Cases](https://www.notion.so/ctemplate/Refactoring-Cases-99d793ab68e64c96b81349ea0d95d2ae?pvs=4)
             - ![img.png](img.png)
         - [강연자료 / Refactoring](https://www.notion.so/ctemplate/Refactoring-ad19620dc9224451a0353e777978ea9a?pvs=4)
         - [Testing and Refactoring Legacy Code (1)](https://www.notion.so/ctemplate/Testing-and-Refactoring-Legacy-Code-1-3ad12cfc15d14e7bb22ff99f1f0d411d?pvs=4)
         - [Approval Testing & Coverage (1)](https://www.notion.so/ctemplate/Approval-Testing-Coverage-1-20c8d4a85f994383bf4047ad042c2bd0?pvs=4)
         - [Exploratory Refactoring](https://www.notion.so/ctemplate/Exploratory-Refactoring-4bc4dd134b364f8c801176779cebeced?pvs=4)
     - 회사
  - Xmind
  - keynote

[//]: # (![Create new topic options]&#40;new_topic_options.png&#41;{ width=290 }{border-effect=line})

## Write content
%product% supports two types of markup: Markdown and XML.
When you create a new help article, you can choose between two topic types, but this doesn't mean you have to stick to a single format.
You can author content in Markdown and extend it with semantic attributes or inject entire XML elements.

## Inject XML
For example, this is how you inject a procedure:

<procedure title="Inject a procedure" id="inject-a-procedure">
    <step>
        <p>Start typing and select a procedure type from the completion suggestions:</p>
        <img src="completion_procedure.png" alt="completion suggestions for procedure" border-effect="line"/>
    </step>
    <step>
        <p>Press <shortcut>Tab</shortcut> or <shortcut>Enter</shortcut> to insert the markup.</p>
    </step>
</procedure>

## Add interactive elements

### Tabs
To add switchable content, you can make use of tabs (inject them by starting to type `tab` on a new line):

<tabs>
    <tab title="Markdown">
        <code-block lang="plain text">![Alt Text](new_topic_options.png){ width=450 }</code-block>
    </tab>
    <tab title="Semantic markup">
        <code-block lang="xml">
            <![CDATA[<img src="new_topic_options.png" alt="Alt text" width="450px"/>]]></code-block>
    </tab>
</tabs>

### Collapsible blocks
Apart from injecting entire XML elements, you can use attributes to configure the behavior of certain elements.
For example, you can collapse a chapter that contains non-essential information:

#### Supplementary info {collapsible="true"}
Content under such header will be collapsed by default, but you can modify the behavior by adding the following attribute:
`default-state="expanded"`

### Convert selection to XML
If you need to extend an element with more functions, you can convert selected content from Markdown to semantic markup.
For example, if you want to merge cells in a table, it's much easier to convert it to XML than do this in Markdown.
Position the caret anywhere in the table and press <shortcut>Alt+Enter</shortcut>:

<img src="convert_table_to_xml.png" alt="Convert table to XML" width="706" border-effect="line"/>

## Feedback and support
Please report any issues, usability improvements, or feature requests to our
<a href="https://youtrack.jetbrains.com/newIssue?project=WRS">YouTrack project</a>
(you will need to register).

You are welcome to join our
<a href="https://join.slack.com/t/writerside/shared_invite/zt-1hnvxnl0z-Nc6RWXTppRI2Oc566vumYw">public Slack workspace</a>.
Before you do, please read our [Code of conduct](https://plugins.jetbrains.com/plugin/20158-writerside/docs/writerside-code-of-conduct.html).
We assume that you’ve read and acknowledged it before joining.

You can also always send an email to [writerside@jetbrains.com](mailto:writerside@jetbrains.com).

<seealso>
    <category ref="wrs">
        <a href="https://plugins.jetbrains.com/plugin/20158-writerside/docs/markup-reference.html">Markup reference</a>
        <a href="https://plugins.jetbrains.com/plugin/20158-writerside/docs/manage-table-of-contents.html">Reorder topics in the TOC</a>
        <a href="https://plugins.jetbrains.com/plugin/20158-writerside/docs/local-build.html">Build and publish</a>
        <a href="https://plugins.jetbrains.com/plugin/20158-writerside/docs/configure-search.html">Configure Search</a>
    </category>
</seealso>