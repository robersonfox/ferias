# FÉRIAS

#### NGINX LOCATIONS

 ```
 location /rest/ {
        proxy_set_header Accept-Encoding "";
        proxy_buffering off;
        proxy_pass http://127.0.0.1:8080;
    }
```
```
location /login/ {
        proxy_set_header Accept-Encoding "";
        proxy_buffering off;
        proxy_pass http://127.0.0.1:8080;
}
```


```
location /bower_components/ {
   alias /home/robersonfox/eclipse-workspace/2-vocation-front/bower_components/;
    autoindex off;
    expires 30d;
}```
