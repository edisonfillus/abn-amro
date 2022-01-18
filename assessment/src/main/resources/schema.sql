/*===========================================================================*/
/*                    DDL Scripts for H2 (Unit Test) and MySQL 8             */
/*===========================================================================*/

/*==============================================================*/
/* Clean                                                        */
/*==============================================================*/
DROP SCHEMA IF EXISTS RECIPES;

/*==============================================================*/
/* Schema: RECIPES                                              */
/*==============================================================*/
CREATE SCHEMA RECIPES;

/*==============================================================*/
/* Table: RECIPE                                                */
/*==============================================================*/

CREATE TABLE RECIPE (
     RECIPE_ID                      BIGINT              NOT NULL AUTO_INCREMENT,
     RECIPE_REF                     VARCHAR(100)        NOT NULL,
     RECIPE_NAME                    VARCHAR(100)        NOT NULL,
     IS_VEGETARIAN                  BOOLEAN             NOT NULL,
     SUITABLE_FOR                   INT                 NOT NULL,
     CREATED_AT                     TIMESTAMP           NOT NULL,
     constraint PK_RECIPE primary key (RECIPE_ID),
     constraint UN_RECIPE_RECIPE_REF unique (RECIPE_REF)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipes';*/
CREATE INDEX IX_RECIPE_CREATED_AT ON RECIPE(CREATED_AT DESC);

/*==============================================================*/
/* Table: INGREDIENT                                            */
/*==============================================================*/

CREATE TABLE INGREDIENT (
    RECIPE_ID                       BIGINT              NOT NULL,
    INGREDIENT_DESCRIPTION          VARCHAR(100)        NOT NULL,
    INGREDIENT_ORDER                INT                 NOT NULL,
    constraint FK_INGREDIENT_RECIPE_ID foreign key (RECIPE_ID)
        references RECIPE(RECIPE_ID),
    constraint UN_INGREDIENT_INGREDIENT_ORDER unique(RECIPE_ID,INGREDIENT_ORDER)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipe ingredients';*/


/*==============================================================*/
/* Table: COOKING_INSTRUCTION                                   */
/*==============================================================*/

CREATE TABLE COOKING_INSTRUCTION (
    RECIPE_ID                       BIGINT              NOT NULL,
    COOKING_INSTRUCTION_DESCRIPTION VARCHAR(100)        NOT NULL,
    COOKING_INSTRUCTION_ORDER       INT                 NOT NULL,
    constraint FK_COOKING_INSTRUCTION_RECIPE_ID foreign key (RECIPE_ID)
        references RECIPE(RECIPE_ID),
    constraint UN_COOKING_INSTRUCTION_ORDER unique(RECIPE_ID,COOKING_INSTRUCTION_ORDER)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipe cooking instructions';*/


/*==============================================================*/
/* Table: USER_ACCOUNT                                          */
/*==============================================================*/

CREATE TABLE USER_ACCOUNT (
    USER_ID                      BIGINT               NOT NULL AUTO_INCREMENT,
    USER_NAME                    VARCHAR(100)         NOT NULL,
    PASSWORD                     VARCHAR(100)         NOT NULL,
    constraint PK_USER primary key (USER_ID),
    constraint UN_USER_USER_NAME unique (USER_NAME)
);


/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/

CREATE TABLE USER_ROLE (
   USER_ID                      BIGINT               NOT NULL,
   ROLE_ID                      INTEGER              NOT NULL,
   constraint PK_USER_ROLE primary key (USER_ID, ROLE_ID),
   constraint FK_USER_ROLE_USER_ID foreign key (USER_ID)
       references USER_ACCOUNT(USER_ID)
);

/*==============================================================*/
/* Data: Initial Test Users                                     */
/*==============================================================*/

INSERT INTO USER_ACCOUNT(USER_NAME, PASSWORD) VALUES ('viewer', '$2a$10$XPiwAXcD3Q5Ml9n3YXVJPuLoAG/idCkDB0IZR.Ok1T.vFybTaeNqe'); /*123456*/
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (1, 1);

INSERT INTO USER_ACCOUNT(USER_NAME, PASSWORD) VALUES ('editor', '$2a$10$UhXj9vtHPwMlv4Ndi3UAs.xBUE7HOEjIkCdaJNe2.vd5BwxpC7b72'); /*123456*/
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES (2, 2);
