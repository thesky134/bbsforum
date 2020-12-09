#创建数据库
DROP DATABASE IF EXISTS forum;
CREATE DATABASE forum;

#使用数据库
USE forum;

#创建角色
CREATE TABLE chara (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) UNIQUE NOT NULL,
    create_time DATETIME NOT NULL
);
# user, admin, superadmin

#创建用户表
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(25) UNIQUE NOT NULL,
    email VARCHAR(64) UNIQUE NOT NULL,
    passwd CHAR(32) NOT NULL,
    salt CHAR(16) NOT NULL,
    phone VARCHAR(20),
    job_type VARCHAR(32),
    job_location VARCHAR(64),
    personal_signal VARCHAR(108),
    picture VARCHAR(16),
    chara_id INT NOT NULL,
    score INT UNSIGNED NOT NULL,
    today_score TINYINT UNSIGNED NOT NULL,
    create_time DATETIME NOT NULL,
    last_login_time DATETIME,
    is_disabled TINYINT UNSIGNED NOT NULL,
    other VARCHAR(64),
    FOREIGN KEY(chara_id) REFERENCES chara(id)
);

#创建分类
CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) UNIQUE NOT NULL,
    introduction VARCHAR(108) NOT NULL,
    create_time DATETIME NOT NULL,
    modify_time DATETIME
);



#创建帖子表
CREATE TABLE post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(64) NOT NULL,
    content VARCHAR(3100) NOT NULL,
    create_time DATETIME NOT NULL,
    modify_time DATETIME,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    is_hidden TINYINT UNSIGNED NOT NULL,
    is_deleted TINYINT UNSIGNED NOT NULL,
    is_excellent TINYINT UNSIGNED NOT NULL,
    is_top TINYINT UNSIGNED NOT NULL,
    reward INT UNSIGNED,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(category_id) REFERENCES category(id)
);

#创建评论表
CREATE TABLE comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR(1100) NOT NULL,
    create_time DATETIME NOT NULL,
    modify_time DATETIME,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    is_hidden TINYINT UNSIGNED NOT NULL,
    is_deleted TINYINT UNSIGNED NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(post_id) REFERENCES post(id)
);

#创建用户关于帖子的状态：赞，踩，喜欢，浏览
#state字段：1表示赞，2表示踩，3表示喜欢，4表示浏览
CREATE TABLE user_post_state (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    state TINYINT UNSIGNED NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(post_id) REFERENCES post(id)
);

#创建用户关于评论的状态：赞，踩，喜欢
#state字段：1表示赞，2表示踩，3表示喜欢
CREATE TABLE user_comment_state (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    comment_id INT NOT NULL,
    state TINYINT UNSIGNED NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id),
    FOREIGN KEY(comment_id) REFERENCES comment(id)
);

#添加角色
INSERT INTO chara (
    NULL, "general", NOW()
);
INSERT INTO chara (
    NULL, "admin", NOW()
);
INSERT INTO chara (
    NULL, "superadmin", NOW()
)



