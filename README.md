# DB111TermProject-411077012-410872026
1.ER圖 : 根據題目敘述建立，其中訂單實體與其他實體關係用雙線連接，因為每個訂單生成一定會有一個顧客含支付的信用卡、一項產品、及負責運送訂單的運輸公司。

2.Schema : 根據ER圖製作，其中商品與訂單的關係為多對多，所以有另外建一個Relational Table 做紀錄。

3.TableSizeCheck.java 可以查看每個Table的大小； TableSizeCheckResult.jpg 為查看後結果圖

4.查詢 Q1~Q4 查詢語句含結果圖 在 Queries1-5.docx ; Q5的程式碼在 mariaDB.java

5.SQL_TableCreate.txt 為每個table的show create table

