# Déploiement d'une Application Spring Boot sur Kubernetes avec AWS

Ce projet documente le processus de déploiement d'une application Spring Boot (stock-ms) dans un cluster Kubernetes local (Minikube) sur une instance AWS Ubuntu.

## Architecture

```
AWS Cloud
└── Instance Ubuntu
    ├── Docker
    ├── Minikube
    ├── Kubectl
    └── Application Spring Boot
        ├── Namespace JEE
        │   └── Déploiement en ligne de commande
        └── Namespace DevOps
            └── Déploiement déclaratif (YAML)
```

## Prérequis

### Infrastructure
- Instance AWS Ubuntu
- Au moins 4 Go de RAM
- Au moins 20 Go d'espace disque

### Logiciels
- Docker
- Minikube
- Kubectl
- Maven
- Git
- JDK (Java Development Kit)

## Installation

### 1. Configuration de l'Instance AWS

```bash
# Mise à jour du système
sudo apt update
sudo apt upgrade -y

# Installation des dépendances
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
```

### 2. Installation de Docker

```bash
# Ajout du repo Docker
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"

# Installation
sudo apt update
sudo apt install -y docker-ce

# Configuration du groupe docker
sudo usermod -aG docker $USER
```

### 3. Installation de Kubectl

```bash
# Téléchargement et installation de kubectl
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

### 4. Installation de Minikube

```bash
# Téléchargement et installation de Minikube
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube /usr/local/bin/
```

## Configuration

### 1. Démarrage de Minikube

```bash
# Démarrage avec driver Docker
minikube start --driver=docker

# Vérification du statut
minikube status
```

### 2. Configuration des Namespaces

```bash
# Création des namespaces
kubectl create namespace jee
kubectl create namespace devops
```

## Déploiement

### 1. Préparation de l'Application

```bash
# Build du projet Spring Boot
mvn clean package

# Construction de l'image Docker
docker build -t stock-ms:1.0 .
```

### 2. Déploiement en Ligne de Commande (Namespace JEE)

```bash
# Déploiement du pod
kubectl run stock-ms --image=stock-ms:1.0 -n jee

# Exposition du service
kubectl expose pod stock-ms --type=NodePort --port=8080 -n jee
```

### 3. Déploiement Déclaratif (Namespace DevOps)

```yaml
# deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: stock-ms
  namespace: devops
spec:
  replicas: 2
  selector:
    matchLabels:
      app: stock-ms
  template:
    metadata:
      labels:
        app: stock-ms
    spec:
      containers:
      - name: stock-ms
        image: stock-ms:1.0
        ports:
        - containerPort: 8080
```

```bash
# Application du déploiement
kubectl apply -f deployment.yaml
```

## Vérification

### 1. Vérification des Déploiements

```bash
# Vérification des pods
kubectl get pods -n jee
kubectl get pods -n devops

# Vérification des services
kubectl get services -n jee
kubectl get services -n devops
```

### 2. Accès à l'Application

```bash
# Obtention de l'URL Minikube
minikube service stock-ms -n jee --url
```

## Dépannage

### Problèmes Courants

1. **Minikube ne démarre pas**
```bash
# Vérification des ressources
minikube start --alsologtostderr -v=8

# Nettoyage
minikube delete
minikube start --driver=docker
```

2. **Pods en état "Pending" ou "CrashLoopBackOff"**
```bash
# Vérification des logs
kubectl describe pod [POD_NAME] -n [NAMESPACE]
kubectl logs [POD_NAME] -n [NAMESPACE]
```

3. **Problèmes d'Image Docker**
```bash
# Chargement de l'image dans Minikube
minikube image load stock-ms:1.0
```

## Maintenance

### Mise à jour de l'Application

```bash
# Build de la nouvelle version
mvn clean package
docker build -t stock-ms:2.0 .

# Mise à jour du déploiement
kubectl set image deployment/stock-ms stock-ms=stock-ms:2.0 -n devops
```

### Scaling

```bash
# Scaling manuel
kubectl scale deployment stock-ms --replicas=3 -n devops
```

## Bonnes Pratiques

1. **Sécurité**
   - Utiliser des images de base officielles
   - Configurer les limites de ressources
   - Implémenter les network policies

2. **Monitoring**
   - Activer les métriques Kubernetes
   - Configurer les liveness/readiness probes
   - Mettre en place des alertes

3. **Backup**
   - Sauvegarder les fichiers YAML
   - Documenter les configurations
   - Maintenir un historique des versions

## Contribution

1. Forker le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Créer une Pull Request

## Licence

Ce projet est sous licence MIT.

## Auteurs

- Maimouna Ndiaye