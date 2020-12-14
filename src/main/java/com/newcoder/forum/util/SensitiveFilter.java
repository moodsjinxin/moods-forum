package com.newcoder.forum.util;

import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author moods
 * @version 1.0.0
 * @ClassName SensetiveFilter.java
 * @Description 敏感词过滤
 * @createTime 11/15/2020 10:40 PM
 */

@Component
public class SensitiveFilter {
    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    // 替换符
    private static final String REPLACEMENT = "***";

    //根节点
    private TrieNode rootNode =  new TrieNode();

    @PostConstruct
    public void init(){
        try (
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ) {

            String keyword;
            while ( (keyword = reader.readLine()) != null ) {
                // 添加到前缀树
                this.addKetWord(keyword);
            }

        } catch (IOException e) {
            logger.error("加载敏感词文件失败："+ e.getMessage());
        }
    }

    /**
     *
     * 运用前缀树过滤敏感词
     * @param text 带过滤的文本
     * @return 过滤后的文件
     *
     * */

    public String filter(String text) {

        if (StringUtils.isBlank(text)){
            return null;
        }

        //指针1
        TrieNode tempNode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;

        // result
        StringBuilder sb = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)) {
                // 若指针1是根节点，将此符号计入结果，让指针2向下走一步
                if(tempNode == rootNode) {
                    sb.append(c);
                    begin++;
                }
                //指针3必须向下走一步
                position++;
                continue;
            }

            //检查下一节点
            tempNode = tempNode.getSubNode(c);
            if ( tempNode == null ) {
                // 以begin 开头的字符串不是敏感词
                sb.append(text.charAt(begin));
                // 进入下一个位置
                position = ++begin;
                // 重新指向根节点
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                // 发现敏感词, 将begin-position字符串替换掉
                sb.append(REPLACEMENT);
                //进入下一个位置
                begin = ++position;
                //重新指向根节点
                tempNode = rootNode;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        sb.append(text.substring(begin));

        return sb.toString();

    }

    private boolean isSymbol(Character c) {
        // 0x2E80~0x9FFF 是东亚文字范围
        return CharUtils.isAsciiAlphanumeric(c) && ( c<0x2E80 || c>0x9FFF);
    }


    // 将敏感词添加到前缀树中
    public  void addKetWord(String key) {
        TrieNode tempNode = rootNode;

        for ( int i=0 ; i<key.length(); i++) {
            char c = key.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null ) {
                //初始化子结点
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            //指向子结点， 进入下一轮循环

            tempNode = subNode;

            // 设置标志位

            if ( i == key.length()-1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }



    //前缀树
    private class TrieNode {
        // 结束标识
        private boolean isKeywordEnd = false;

        // key 是 下级字符， value 是下级节点
        private Map<Character, TrieNode> subNode = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        // 添加子结点
        public void addSubNode(Character cha, TrieNode node) {
            subNode.put(cha, node);
        }

        // 获取子结点
        public TrieNode getSubNode(Character c) {
            return subNode.get(c);
        }
    }
}
