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
     RECIPE_NAME                    VARCHAR(100)        NOT NULL,
     IS_VEGETARIAN                  BOOLEAN             NOT NULL,
     SUITABLE_FOR                   INT                 NOT NULL,
     CREATED_AT                     TIMESTAMP           NOT NULL,
     constraint PK_RECIPE primary key (RECIPE_ID)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipes';*/

/*==============================================================*/
/* Table: INGREDIENT                                            */
/*==============================================================*/

CREATE TABLE INGREDIENT (
    RECIPE_ID                       BIGINT              NOT NULL,
    INGREDIENT_DESCRIPTION          VARCHAR(100)        NOT NULL,
    INGREDIENT_ORDER                INT                 NOT NULL,
    constraint FK_INGREDIENT_RECIPE foreign key (RECIPE_ID)
        references RECIPE(RECIPE_ID),
    constraint UN_INGREDIENT_ORDER unique(RECIPE_ID,INGREDIENT_ORDER)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipe ingredients';*/


/*==============================================================*/
/* Table: COOKING_INSTRUCTION                                   */
/*==============================================================*/

CREATE TABLE COOKING_INSTRUCTION (
    RECIPE_ID                       BIGINT              NOT NULL,
    COOKING_INSTRUCTION_DESCRIPTION VARCHAR(100)        NOT NULL,
    COOKING_INSTRUCTION_ORDER       INT                 NOT NULL,
    constraint FK_COOKING_INSTRUCTION_RECIPE foreign key (RECIPE_ID)
        references RECIPE(RECIPE_ID),
    constraint UN_COOKING_INSTRUCTION_ORDER unique(RECIPE_ID,COOKING_INSTRUCTION_ORDER)
);/* DEFAULT CHARSET=utf8 COMMENT 'Store recipe cooking instructions';*/
