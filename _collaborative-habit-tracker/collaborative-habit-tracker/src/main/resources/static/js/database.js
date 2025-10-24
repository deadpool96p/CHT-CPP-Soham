// Fake Database Simulation
const HabitTrackerDB = {
    // User data
    user: JSON.parse(localStorage.getItem('userData')) || {
        id: 1,
        name: 'Demo User',
        email: 'user@example.com',
        joinDate: '2024-01-15',
        theme: 'blue'
    },
    
    // Habits data
    habits: JSON.parse(localStorage.getItem('habitsData')) || [
        {
            id: 1,
            name: 'Morning Meditation',
            description: '10 minutes of mindfulness meditation',
            category: 'mindfulness',
            frequency: 'daily',
            time: '08:00',
            streak: 7,
            completed: true,
            goal: 10,
            unit: 'minutes',
            reminders: true,
            difficulty: 'easy'
        },
        {
            id: 2,
            name: 'Evening Run',
            description: '30 minutes of running',
            category: 'health',
            frequency: 'daily',
            time: '18:00',
            streak: 5,
            completed: false,
            goal: 30,
            unit: 'minutes',
            reminders: true,
            difficulty: 'medium'
        },
        {
            id: 3,
            name: 'Reading',
            description: 'Read 50 pages per week',
            category: 'learning',
            frequency: 'weekly',
            time: '20:00',
            streak: 2,
            completed: false,
            goal: 50,
            unit: 'pages',
            reminders: false,
            difficulty: 'easy'
        }
    ],
    
    // Groups data
    groups: JSON.parse(localStorage.getItem('groupsData')) || [
        {
            id: 1,
            name: 'Morning Routines',
            description: 'A community dedicated to building consistent morning habits',
            members: 245,
            userStreak: 7,
            isMember: true
        },
        {
            id: 2,
            name: 'Fitness Enthusiasts',
            description: 'Stay motivated with daily workouts and fitness challenges',
            members: 512,
            userStreak: 14,
            isMember: true
        },
        {
            id: 3,
            name: 'Meditation & Mindfulness',
            description: 'Practice daily meditation together',
            members: 1200,
            userStreak: 0,
            isMember: false
        }
    ],
    
    // Analytics data
    analytics: JSON.parse(localStorage.getItem('analyticsData')) || {
        successRate: 85,
        currentStreak: 42,
        totalCompleted: 156,
        activeHabits: 12,
        monthlyStats: {
            morningHabits: 92,
            eveningHabits: 78,
            bestStreak: 15,
            avgDaily: 3.2
        }
    },
    
    // Save data to localStorage
    saveData: function() {
        localStorage.setItem('userData', JSON.stringify(this.user));
        localStorage.setItem('habitsData', JSON.stringify(this.habits));
        localStorage.setItem('groupsData', JSON.stringify(this.groups));
        localStorage.setItem('analyticsData', JSON.stringify(this.analytics));
    },
    
    // Habit methods
    addHabit: function(habitData) {
        const newHabit = {
            id: this.habits.length + 1,
            ...habitData,
            streak: 0,
            completed: false
        };
        this.habits.push(newHabit);
        this.saveData();
        return newHabit;
    },
    
    completeHabit: function(habitId) {
        const habit = this.habits.find(h => h.id === habitId);
        if (habit) {
            habit.completed = true;
            habit.streak += 1;
            this.analytics.totalCompleted += 1;
            this.saveData();
        }
    },
    
    deleteHabit: function(habitId) {
        this.habits = this.habits.filter(h => h.id !== habitId);
        this.saveData();
    },
    
    // Group methods
    joinGroup: function(groupId) {
        const group = this.groups.find(g => g.id === groupId);
        if (group) {
            group.isMember = true;
            group.userStreak = 1;
            group.members += 1;
            this.saveData();
        }
    },
    
    leaveGroup: function(groupId) {
        const group = this.groups.find(g => g.id === groupId);
        if (group) {
            group.isMember = false;
            group.userStreak = 0;
            group.members -= 1;
            this.saveData();
        }
    },
    
    // User methods
    updateProfile: function(userData) {
        this.user = { ...this.user, ...userData };
        this.saveData();
    },
    
    // Share progress
    shareProgress: function() {
        const progress = {
            successRate: this.analytics.successRate,
            currentStreak: this.analytics.currentStreak,
            totalCompleted: this.analytics.totalCompleted,
            message: `Check out my progress on HabitTracker! I've completed ${this.analytics.totalCompleted} habits with a ${this.analytics.successRate}% success rate!`
        };
        return progress;
    }
};

// Utility Functions
const AppUtils = {
    // Show notification
    showNotification: function(message, type = 'success') {
        const existingNotification = document.querySelector('.notification');
        if (existingNotification) {
            existingNotification.remove();
        }
        
        const notification = document.createElement('div');
        notification.className = 'notification';
        const icon = type === 'success' ? 'fa-check-circle' : 'fa-exclamation-circle';
        const color = type === 'success' ? 'var(--success-color)' : 'var(--warning-color)';
        
        notification.innerHTML = `
            <div class="notification-content">
                <i class="fas ${icon}"></i>
                <span>${message}</span>
            </div>
        `;
        
        if (!document.querySelector('#notification-styles')) {
            const styles = document.createElement('style');
            styles.id = 'notification-styles';
            styles.textContent = `
                .notification {
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    background: var(--card-bg);
                    color: var(--text-color);
                    padding: 15px 20px;
                    border-radius: var(--border-radius);
                    box-shadow: var(--box-shadow);
                    z-index: 10000;
                    border-left: 4px solid ${color};
                    animation: slideInRight 0.3s ease;
                }
                
                .notification-content {
                    display: flex;
                    align-items: center;
                    gap: 10px;
                }
                
                .notification-content i {
                    color: ${color};
                }
                
                @keyframes slideInRight {
                    from {
                        transform: translateX(100%);
                        opacity: 0;
                    }
                    to {
                        transform: translateX(0);
                        opacity: 1;
                    }
                }
            `;
            document.head.appendChild(styles);
        }
        
        document.body.appendChild(notification);
        
        setTimeout(() => {
            notification.style.animation = 'slideInRight 0.3s ease reverse';
            setTimeout(() => notification.remove(), 300);
        }, 3000);
    },
    
    // Share functionality
    shareProgress: function() {
        const progress = HabitTrackerDB.shareProgress();
        
        if (navigator.share) {
            navigator.share({
                title: 'My HabitTracker Progress',
                text: progress.message,
                url: 'https://habittracker.com/user/profile'
            })
            .then(() => AppUtils.showNotification('Progress shared successfully!'))
            .catch(error => AppUtils.showNotification('Sharing failed', 'error'));
        } else {
            // Fallback: copy to clipboard
            navigator.clipboard.writeText(progress.message + ' https://habittracker.com/user/profile')
                .then(() => AppUtils.showNotification('Progress copied to clipboard!'))
                .catch(() => AppUtils.showNotification('Failed to copy to clipboard', 'error'));
        }
    },
    
    // Create group
    createGroup: function() {
        const groupName = prompt('Enter group name:');
        if (groupName) {
            const newGroup = {
                id: HabitTrackerDB.groups.length + 1,
                name: groupName,
                description: 'A new habit group',
                members: 1,
                userStreak: 0,
                isMember: true
            };
            HabitTrackerDB.groups.push(newGroup);
            HabitTrackerDB.saveData();
            AppUtils.showNotification(`Group "${groupName}" created successfully!`);
        }
    },
    
    // Join group
    joinGroup: function(groupId = null) {
        if (groupId) {
            HabitTrackerDB.joinGroup(groupId);
            AppUtils.showNotification('Joined group successfully!');
        } else {
            const groupCode = prompt('Enter group code:');
            if (groupCode) {
                AppUtils.showNotification(`Request to join group with code ${groupCode} sent!`);
            }
        }
    }
};

// Initialize app
document.addEventListener('DOMContentLoaded', function() {
    // Load theme
    const savedTheme = localStorage.getItem('selectedTheme') || 'blue';
    document.body.className = `theme-${savedTheme}`;
    
    // Load user data
    const userData = HabitTrackerDB.user;
    document.querySelectorAll('.user-name').forEach(el => {
        el.textContent = userData.name;
    });
    document.querySelectorAll('.user-email').forEach(el => {
        el.textContent = userData.email;
    });
    document.querySelectorAll('.welcome-message').forEach(el => {
        el.textContent = `Welcome back, ${userData.name.split(' ')[0]}!`;
    });
    document.querySelectorAll('.user-avatar').forEach(avatar => {
        avatar.textContent = userData.name.charAt(0).toUpperCase();
    });
});