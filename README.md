# ferias



# NGINX LOCATIONS
 	
    REST
    ```
     location /rest/ {
            proxy_set_header Accept-Encoding "";
            proxy_buffering off;
            proxy_pass http://127.0.0.1:8080;
        }
    ```

    LOGIN
	```location /login/ {
            proxy_set_header Accept-Encoding "";
            proxy_buffering off;
            proxy_pass http://127.0.0.1:8080;
    }
    ```
    