package com.mmc.Tuan.Data;
import javax.persistence.*;

@Entity
@Table(name = "post_mmc")
public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="post_id")
        private long postId;

        @Column(name = "post_name")
        private String postName;

        @Column(name = "post_description")
        private String postDescription;

        @Column(name = "post_content")
        private String postContent;

        @Column(name = "post_type")
        private String postType;

        @Column(name = "post_year")
        private long postYear;

        @Lob
        @Column(name="post_pic")
        private String postPic;

        public Post(){

        }

        public Post( String postName, String postDescription, String postContent, String postType, long postYear, String postPic) {
                this.postName = postName;
                this.postDescription = postDescription;
                this.postContent = postContent;
                this.postType = postType;
                this.postYear = postYear;
                this.postPic = postPic;
        }

        public long getPostId() {
                return postId;
        }

        public void setPostId(long postId) {
                this.postId = postId;
        }

        public String getPostName() {
                return postName;
        }

        public void setPostName(String postName) {
                this.postName = postName;
        }

        public String getPostDescription() {
                return postDescription;
        }

        public void setPostDescription(String postDescription) {
                this.postDescription = postDescription;
        }

        public String getPostContent() {
                return postContent;
        }

        public void setPostContent(String postContent) {
                this.postContent = postContent;
        }

        public String getPostType() {
                return postType;
        }

        public void setPostType(String postType) {
                this.postType = postType;
        }

        public long getPostYear() {
                return postYear;
        }

        public void setPostYear(long postYear) {
                this.postYear = postYear;
        }

        public String getPostPic() {
                return postPic;
        }

        public void setPostPic(String postPic) {
                this.postPic = postPic;
        }

        @Override
        public String toString() {
                return "Info Post: [Id = " + postId + ", Name = " + postName + ", Description = " + postDescription
                        + ", Content = " + postContent + ", Type = " + postType + ", Year = " + postYear + "]";
        }
}
