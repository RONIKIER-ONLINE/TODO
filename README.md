<h1>TODO</h1>
<b>Task tracking system</b><br/><br/>
<blockquote>.doc/TODO.eap</blockquote>Sparx Systems Enterprise Architect model

<h3>neo4j BACKUP (Win)</h3>
<h6>
>cd c:\Users\Admin\.Neo4jDesktop\neo4jDatabases\database-<i>xxxxxx</i>\installation-4.0.3\bin<br/><br/>
>neo4j-admin backup --from=localhost:6362 --backup-dir=c:/magazyn/archive/200602 --database=neo4j --pagecache=4G
</h6>

<h3>neo4j RESTORE (Win)</h3>
<h6>
>cd c:\Users\Admin\.Neo4jDesktop\neo4jDatabases\database-<i>xxxxxx</i>\installation-4.0.3\bin<br/><br/>
>neo4j stop<br/><br/>
>neo4j-admin restore --from=c:/magazyn/archive/200602/neo4j --database=neo4j --force<br/><br/>
>neo4j start
</h6>

<h3>neo4j CLEAR (Terminal)</h3>
<code>
MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
</code><br>

<h3>neo4j Tasks + subtasks (Terminal)</h3>
<code>
MATCH (task:Task {name:'Karma'}) RETURN task UNION MATCH ((mainTask:Task {name:'Karma'})-[r]->(task:Task)) RETURN task
</code>

<h3>cloud (Google)</h3>
<h6>
https://ronikier.uc.r.appspot.com/
</h6>

<h3>Windows Service</h3>
<h6>
https://downloads.apache.org/commons/daemon/binaries/windows/
</h6>
<blockquote>TODOService.exe //IS//TODOService --Install="C:\magazyn\service\TODOService.exe" --Jvm=auto --Startup=auto --StartMode=jvm --Classpath="C:\magazyn\service\todo-application-1.0-SNAPSHOT.jar" --StartClass=online.ronikier.todo.Application</blockquote>


