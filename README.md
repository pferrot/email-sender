# email-sender

## Building
In order to build this module, you will need to add the public JBoss repository, e.g. in ~/.m2/settings.xml as follows:
```
<settings>
 	<profiles>
        <profile>
            <id>standard-extra-repos</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <repositories>
				<repository>
                    <id>jboss</id>
                    <url>http://repository.jboss.com</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                </repository>
            </repositories>
		</profile>
	</profiles>
</settings>
