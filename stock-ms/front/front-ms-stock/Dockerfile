FROM nginx:alpine

WORKDIR /webapp

COPY nginx.conf /etc/nginx/nginx.conf

COPY artifact/ /usr/share/nginx/html

#exposing internal port
EXPOSE 80
