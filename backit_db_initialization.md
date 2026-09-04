```sql
CREATE DATABASE backit;
```

```sql
USE backit;
```

```sql
CREATE TABLE board (
    id BIGINT auto_increment PRIMARY KEY,
    title varchar(255) NULL,
    created_at date NULL,
    updated_at date NULL);
```

```sql
CREATE TABLE image (
    id BIGINT auto_increment PRIMARY KEY,
    bytes mediumblob NULL, type varchar(255) NULL,
    alternate_text varchar(255) NULL,
    caption varchar(255) NULL,
    created_at date NULL,
    updated_at date NULL);
```

```sql
CREATE TABLE board_entry (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order` SMALLINT NOT NULL,
    board_id BIGINT NOT NULL,
    image_id BIGINT NOT NULL,
    created_at DATE NULL,
    updated_at DATE NULL,
    CONSTRAINT fk_board_entry_board_id FOREIGN KEY (board_id) REFERENCES board (id),
    CONSTRAINT fk_board_entry_image_id FOREIGN KEY (image_id) REFERENCES image (id)
);
```