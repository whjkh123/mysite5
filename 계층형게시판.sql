CREATE TABLE rboard (no NUMBER,
                     user_no NUMBER NOT NULL,
                     title VARCHAR2(500),
                     content VARCHAR2(4000),
                     hit NUMBER,
                     reg_date DATE,
                     group_no NUMBER,
                     order_no NUMBER,
                     depth NUMBER,
                     PRIMARY KEY (no),
                     constraint rboard_fk foreign key (user_no) references users (no)
                    );

SELECT  no,
        user_no,
        title,
        content,
        hit,
        reg_date,
        group_no,
        order_no,
        depth
FROM    rboard
ORDER BY group_no desc, order_no asc;

DELETE FROM rboard;

DROP SEQUENCE seq_rboard_no;

CREATE SEQUENCE seq_rboard_no INCREMENT BY 1 START WITH 1;

INSERT INTO rboard VALUES(seq_rboard_no.NEXTVAL, 'test', 'test', default, sysdate, 1);