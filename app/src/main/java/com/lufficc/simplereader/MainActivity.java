package com.lufficc.simplereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.widget.MarkdownView;

public class MainActivity extends BaseActivity {
    MarkdownView markdownView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdownView.setOnPageFinishedListener(new MarkdownView.onPageFinishedListener() {
            @Override
            public void onPageFinished() {
                markdownView.parseMarkdown("> 我们在Android开发过程中一定会用到别人的库，比如squareup公司的OKHttp：\n" +
                        "```\n" +
                        "compile 'com.squareup.okhttp3:okhttp:3.4.1'\n" +
                        "```\n" +
                        "> 这样我们版本更新的时候只需要更改一下版本号就行，而不用去下载jar包，给开发带来了极大的便利，但如果我们自己想上传lib供其他开发者使用呢？那么此教程会带着你一步一步发布自己的library。Let's go!\n" +
                        "\n" +
                        "<!--more-->\n" +
                        "\n" +
                        "## 确定要上传的Library\n" +
                        "如果你有Library可以忽略此步骤，没有的话添加library。在Android Studio中选择File->New->New Module,然后选择一个Library,新建一个Library。这里以新建DemoLibrary为例子。**（注意这里的Library需要后面的Package的名字一致）。**\n" +
                        "![新建一个Library](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804205901.jpg)\n" +
                        "![新建一个Library](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804220140.jpg)\n" +
                        "\n" +
                        "现在项目的结构如下图，接下来就是添加必要的Jcenter的依赖，为上传做准备。\n" +
                        "![项目的结构](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804205947.jpg)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "## 注册账号\n" +
                        "首先去[bintray官网](https://bintray.com/)注册账号，注册完成后验证邮箱，然后登陆进入首页点击View All，选择Maven仓库，新建一个Package，填写Package名字**（注意Package需要和你的Library的名字一致）**\n" +
                        "\n" +
                        "![](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804215327.jpg)\n" +
                        "![](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804215609.jpg)\n" +
                        "\n" +
                        "注意点击你的头像->Your Profile->Edit->Api key,这个先记下来，后面上传要用到。\n" +
                        "![Api key](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804224228.jpg)\n" +
                        "\n" +
                        "新建一个Package\n" +
                        "![新建一个Package](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804215759.jpg)\n" +
                        "![新建一个Package](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804232629.png)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "## 添加依赖\n" +
                        "在整个工程的build.gradle文件中添加`classpath 'com.novoda:bintray-release:0.3.4'`,***注意是整个工程的build.gradle***。\n" +
                        "\n" +
                        "![添加依赖](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804221342.jpg)\n" +
                        "\n" +
                        "接着是在你自己Library（这里是DemoLibrary）的build.gradle的文件中配置自己的信息，复制下面的脚本，改成你自己的信息即可\n" +
                        "``` gradle\n" +
                        "apply plugin: 'com.android.library'\n" +
                        "apply plugin: 'com.novoda.bintray-release'\n" +
                        "\n" +
                        "publish {\n" +
                        "    userOrg = 'lufficc' //你的用户名\n" +
                        "    groupId = 'com.lufficc' //你的唯一的groupId，对应com.squareup.okhttp3:okhttp:3.4.1中的com.squareup.okhttp3\n" +
                        "    artifactId = 'DemoLibrary' //你的library的名字，对应com.squareup.okhttp3:okhttp:3.4.1中的okhttp\n" +
                        "    publishVersion = '0.0.1' //版本号\n" +
                        "    desc = 'This is a demo library to teach how to publish you own library to jcenter with android studio.'\n" +
                        "    website = 'http://lufficc.com/' //建议填写github地址，不过不影响，这里做演示填的自己的网址\n" +
                        " \n" +
                        "    bintrayUser = 'lufficc' //你的用户名\n" +
                        "    bintrayKey = 'Your api key' //在你的账户里面查找\n" +
                        "}\n" +
                        "```\n" +
                        "经过上面的配置，上传成功后那么别人引用你的library的代码就为`compile 'com.lufficc:DemoLibrary:0.0.1'`。\n" +
                        "\n" +
                        "![你自己Library的build.gradle的文件中配置自己的信息](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804221949.jpg)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "## 上传\n" +
                        "经过上面的配置，现在就可以传了，上传之前记得Sync一下Project,然后打开命令行，输入,回车：\n" +
                        "``` gradle\n" +
                        "gradlew clean build bintrayUpload -PdryRun=false\n" +
                        "```\n" +
                        "然后等待几分钟，期间会联网下载依赖的库，最后如果没有问题，会显示BUILD SUCCESSFUL信息，然后去官网查看刚才建的Package，会发现多了你刚才上传的版本号。\n" +
                        "\n" +
                        "![BUILD SUCCESSFUL](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804233412.png)\n" +
                        "![上传成功的Package](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804233452.png)\n" +
                        "\n" +
                        "点进去可以看到有三种引用方式：\n" +
                        "![](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804234201.png)\n" +
                        "\n" +
                        "但是到这里还无法让别人也能引用，目前只是你自己的私人库。下面是添加到Jcenter,非常简单。\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "## 添加到Jcenter\n" +
                        "在上面的页面中点击Add To JCenter，然后随便填写一下comments，点击send，然后工作人员会审核和，你只需等待几个小时，然后会有站内消息提示你已经发布发到Jcenter，这样别人也可以引用你的Library，有没有很自豪的感觉！\n" +
                        "![](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804234627.png)\n" +
                        "![](http://7xp8c8.com1.z0.glb.clouddn.com/QQ%E6%88%AA%E5%9B%BE20160804234750.png)\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "## 更新版本号\n" +
                        "这个非常简单，当你的Libraryd代码更改后，只需要更改一下上面的配置里面的`publishVersion`，运行`gradlew clean build bintrayUpload -PdryRun=false`，就可以更新版本号了。这样，整个过程就结束了，遇到什么问题欢迎评论提出或者私信我。\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "## 总结\n" +
                        "> 其实上传没那么复杂\n" +
                        "\n" +
                        "1. 注册账号\n" +
                        "1. 为自己的Library项目添加依赖，配置信息\n" +
                        "1. 上传，添加到Jcenter\n" +
                        "1. 更新版本号\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        " ******************************************************************* \n" +
                        "\n" +
                        "\n" +
                        "## 常见问题\n" +
                        "1. 如果你的Java doc含有中文导致上传失败，可以尝试在lib的build.gradle添加如下代码：\n" +
                        "``` gradle\n" +
                        " allprojects {\n" +
                        "    tasks.withType(Javadoc) {\n" +
                        "        options{\n" +
                        "            encoding \"UTF-8\"\n" +
                        "            charSet 'UTF-8'\n" +
                        "            links \"http://docs.oracle.com/javase/7/docs/api\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}\n" +
                        "```\n" +
                        "1. 本教程是基于插件[novoda/bintray-release](https://github.com/novoda/bintray-release)的，更多问题可以查看[issues](https://github.com/novoda/bintray-release/issues)或者查看[Wiki](https://github.com/novoda/bintray-release/wiki)。", true);
            }
        });
    }
}
