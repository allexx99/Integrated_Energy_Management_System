# Use an official Node runtime as the base image
FROM node:13.12.0-alpine

# Set the working directory
WORKDIR /app

# Copy both package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Add the rest of the project files
COPY . ./

# Specify the port number the container should expose
EXPOSE 3000

# Run the app
CMD ["npm", "start"]