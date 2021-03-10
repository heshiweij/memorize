package com.xju.memorize.bean;

import java.util.List;

public class UserVocabularyBean {

    /**
     * list
     */
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id
         */
        private Integer id;
        /**
         * word
         */
        private String word;

        /**
         * count
         */
        private String count;

        /**
         * pronunciation
         */
        private String pronunciation;
        /**
         * translate
         */
        private String translate;
        /**
         * picture
         */
        private String picture;
        /**
         * created_at
         */
        private String created_at;
        /**
         * updated_at
         */
        private String updated_at;
        /**
         * example_sentence
         */
        private List<String> example_sentence;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public String getTranslate() {
            return translate;
        }

        public void setTranslate(String translate) {
            this.translate = translate;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public List<String> getExample_sentence() {
            return example_sentence;
        }

        public void setExample_sentence(List<String> example_sentence) {
            this.example_sentence = example_sentence;
        }
    }
}
