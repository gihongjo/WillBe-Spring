Will-Be: AI-Based Platform for Improving Special Children's Behavior
Domain Overview 
Problem Statement
Special education often lacks the resources to effectively monitor, analyze, and improve the behaviors of children with disabilities such as autism, intellectual disabilities, and emotional disorders. Current practices rely heavily on manual data recording, leading to missed opportunities for intervention and support.

Solution
Will-Be is an AI-powered application designed for special education teachers and parents to record, analyze, and summarize children's behaviors. By leveraging behavior tracking, communication logs, and LLM (Large Language Model) analytics, the platform helps educators and caregivers identify patterns, develop strategies, and improve overall education quality.

Technical Overview
Core Features
Behavior Logging

Record incidents with a single click.
View trends with daily, weekly, and monthly graphs.
Daily Notes

Summarize daily activities and notable behaviors.
Provide context for behavior analysis.
Communication Dictionary

Predefine common behaviors and responses.
Share actionable insights with educators and caregivers.
AI-Powered Analysis

Use LLM to analyze logs and daily notes.
Generate summaries and recommendations for behavior improvement.
Historical Data Management

Maintain a secure database for behavior trends.
Facilitate transitions between educators.
Technical Implementation
Architecture
Frontend: Flutter for cross-platform compatibility.
Backend: Spring Boot with REST APIs for robust and scalable operations.
Database: MySQL for secure storage of behavior logs and notes.
AI Integration: ChatGPT API for behavior analysis and pattern detection.
Deployment: Kubernetes (NKS) with CI/CD pipelines.
Key Technologies
Spring Security: Ensures secure authentication and JWT-based authorization.
JPA: Simplifies database operations with ORM.
FlChart: Visualizes behavior trends through interactive graphs.
SharedPreferences: Manages client-side JWT storage.
Workflow
Login & Authentication

OAuth2-based Google login for secure access.
Data Entry

Add child profiles and behavior details.
Real-Time Behavior Logging

Track incidents with timestamps.
Cancel or modify entries.
Visualization

Analyze trends through dynamic graphs.
LLM Analysis

Generate actionable summaries from collected data.
Deployment
CI/CD Pipeline
Source Commit: Code updates trigger automated deployment workflows.
Source Build: Builds Docker images from Spring Boot applications.
Source Deploy: Deploys containers to Kubernetes clusters.
Kubernetes Integration
Pods for each service, ensuring modularity and scalability.
Persistent Volumes (PV) for secure data storage.
Load Balancer for distributing client traffic.
Benefits & Future Goals
Benefits
Reduces the workload of special educators by automating data analysis.
Provides parents and educators with actionable insights to improve interventions.
Ensures continuity of education through detailed behavior histories.
Future Enhancements
Automate behavior detection using video analysis.
Integrate data sharing features for seamless educator transitions.
Expand to other types of disabilities and inclusive education needs.
Contact
Team Lead: Gihong Cho (gihong0409@gmail.com)
Repository: Will-Be GitHub
