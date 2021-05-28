# schema

| type | operation | key | name | super_name | comment |
| --- | --- | --- | --- | --- | --- |
| college | add | id | name | - | 插入指定学院 |
| college | delete | id | - | - | 删除指定学院 |
| profession | add | ... |
| profession | delete | ... |
| xclass | add | ... |
| xclass | delete | ... |
| check | structure | fileName,key | - | - | 检查当前的架构是否和json文件保持一致 |
| check | counts-college | count | - | - | 检查当前学院的个数 |
| check | value-college | parameter(id or name), value | fileName,key | - | 检查当前的college是否和json文件保持一致 |
| !check | counts-profession | collegeId | count | - | 检查某学院下的专业个数是否符合预期 |
| !check | value-profession | parameter(id or name), value | fileName, key | - | 检查当前的profession是否和json文件保持一致 |
| !check | counts-xclass | ... |
| !check | value-xclass | ... |