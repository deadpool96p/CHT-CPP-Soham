// Main application JavaScript
class HabitTracker {
    constructor() {
        this.init();
    }

    init() {
        this.bindEvents();
        this.initializeCharts();
        this.initializeSearch();
    }

    bindEvents() {
        // Bind complete habit buttons - using event delegation for dynamic content
        document.addEventListener('click', (e) => {
            if (e.target.closest('.complete-habit-btn')) {
                this.completeHabit(e.target.closest('.complete-habit-btn'));
            }
            
            // Bind regular success buttons as fallback
            if (e.target.closest('.btn-success') && !e.target.closest('.complete-habit-btn')) {
                this.completeHabit(e.target.closest('.btn-success'));
            }
        });

        // Bind log buttons
        document.addEventListener('click', (e) => {
            if (e.target.closest('.btn-outline-info')) {
                this.logHabit(e.target.closest('.btn-outline-info'));
            }
        });
    }

    completeHabit(button) {
        const habitId = button.getAttribute('data-habit-id');
        const habitCard = button.closest('.habit-card') || button.closest('.habit-item');
        
        // Show loading state
        const originalHTML = button.innerHTML;
        button.innerHTML = '<i class="fas fa-spinner fa-spin me-1"></i> Completing...';
        button.disabled = true;

        // Simulate API call
        setTimeout(() => {
            button.innerHTML = '<i class="fas fa-check me-1"></i> Completed';
            button.classList.remove('btn-success');
            button.classList.add('btn-secondary');
            button.disabled = true;

            // Add completion animation
            if (habitCard) {
                habitCard.style.animation = 'completePulse 0.5s ease-in-out';
                setTimeout(() => {
                    habitCard.style.animation = '';
                }, 500);
            }

            this.showNotification('Habit completed successfully!', 'success');
            
            // Update progress bar if it exists
            this.updateProgressAfterCompletion(habitCard);
            
        }, 1000);
    }

    logHabit(button) {
        const habitId = button.getAttribute('data-habit-id');
        console.log('Logging habit:', habitId);
        this.showNotification('Opening habit log...', 'info');
    }

    updateProgressAfterCompletion(habitCard) {
        // Find progress bar in the habit card and update it
        const progressBar = habitCard.querySelector('.progress-bar');
        const progressText = habitCard.querySelector('.progress-bar span');
        const completionText = habitCard.querySelector('.small.text-muted span:first-child');
        
        if (progressBar && progressText && completionText) {
            const currentWidth = parseInt(progressBar.style.width) || 0;
            const newWidth = Math.min(currentWidth + 10, 100); // Increase by 10%
            
            progressBar.style.width = newWidth + '%';
            progressBar.setAttribute('aria-valuenow', newWidth);
            progressText.textContent = newWidth + '%';
            completionText.textContent = newWidth + '% Complete';
        }
    }

    initializeSearch() {
        // Search functionality for habits page
        const searchInput = document.querySelector('input[type="text"]');
        if (searchInput && window.location.pathname.includes('/habits')) {
            searchInput.addEventListener('input', this.debounce((e) => {
                this.searchHabits(e.target.value);
            }, 300));
        }
    }

    searchHabits(query) {
        const habitCards = document.querySelectorAll('.habit-card');
        const searchTerm = query.toLowerCase();
        
        habitCards.forEach(card => {
            const habitName = card.querySelector('h6').textContent.toLowerCase();
            const habitDescription = card.querySelector('.card-text').textContent.toLowerCase();
            
            if (habitName.includes(searchTerm) || habitDescription.includes(searchTerm)) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
        
        console.log('Searching habits for:', query);
    }

    debounce(func, wait) {
        let timeout;
        return function executedFunction(...args) {
            const later = () => {
                clearTimeout(timeout);
                func(...args);
            };
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
        };
    }

    showNotification(message, type = 'info') {
        // Remove existing notifications
        const existingNotifications = document.querySelectorAll('.custom-notification');
        existingNotifications.forEach(notification => notification.remove());

        // Create notification element
        const notification = document.createElement('div');
        notification.className = `alert alert-${type} alert-dismissible fade show custom-notification`;
        notification.style.cssText = 'position: fixed; top: 20px; right: 20px; z-index: 1060; min-width: 300px;';
        notification.innerHTML = `
            <div class="d-flex align-items-center">
                <i class="fas ${this.getNotificationIcon(type)} me-2"></i>
                <span>${message}</span>
            </div>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;

        document.body.appendChild(notification);

        // Auto remove after 5 seconds
        setTimeout(() => {
            if (notification.parentNode) {
                notification.parentNode.removeChild(notification);
            }
        }, 5000);
    }

    getNotificationIcon(type) {
        const icons = {
            'success': 'fa-check-circle',
            'info': 'fa-info-circle',
            'warning': 'fa-exclamation-triangle',
            'danger': 'fa-exclamation-circle'
        };
        return icons[type] || 'fa-info-circle';
    }

    initializeCharts() {
        // Initialize any charts on the page
        const chartElements = document.querySelectorAll('canvas');
        chartElements.forEach(chart => {
            // Additional chart initialization if needed
            // This will be called for any canvas elements on the page
        });
    }

    // Method to handle habit creation form
    initializeHabitForm() {
        const form = document.querySelector('form[th\\:action="@{/habits}"]');
        if (form) {
            form.addEventListener('submit', (e) => {
                this.handleHabitFormSubmit(e);
            });
        }
    }

    handleHabitFormSubmit(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        const habitData = Object.fromEntries(formData);
        
        console.log('Submitting habit:', habitData);
        this.showNotification('Creating new habit...', 'info');
        
        // Simulate form submission
        setTimeout(() => {
            this.showNotification('Habit created successfully!', 'success');
            window.location.href = '/habits';
        }, 1500);
    }
}

// Utility functions
const AppUtils = {
    formatDate(date) {
        return new Date(date).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        });
    },

    formatTime(minutes) {
        const hours = Math.floor(minutes / 60);
        const mins = minutes % 60;
        return hours > 0 ? `${hours}h ${mins}m` : `${mins}m`;
    },

    calculateStreak(completions) {
        if (!completions || completions.length === 0) return 0;
        
        let streak = 0;
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        
        for (let i = completions.length - 1; i >= 0; i--) {
            const completionDate = new Date(completions[i]);
            completionDate.setHours(0, 0, 0, 0);
            
            const diffTime = today - completionDate;
            const diffDays = Math.floor(diffTime / (1000 * 60 * 60 * 24));
            
            if (diffDays === streak) {
                streak++;
            } else {
                break;
            }
        }
        
        return streak;
    },

    // New utility function for habit statistics
    calculateSuccessRate(completions, totalDays) {
        if (totalDays === 0) return 0;
        return Math.round((completions / totalDays) * 100);
    },

    // Format progress percentage with color coding
    getProgressColor(percentage) {
        if (percentage >= 80) return 'success';
        if (percentage >= 60) return 'info';
        if (percentage >= 40) return 'warning';
        return 'danger';
    }
};

// Initialize the application when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    const habitTracker = new HabitTracker();
    
    // Make utils globally available
    window.AppUtils = AppUtils;
    window.HabitTracker = habitTracker;
    
    console.log('CollabHabitTracker initialized successfully!');
});

// Additional global functions for template use
function confirmDelete(habitId, habitName) {
    if (confirm(`Are you sure you want to delete the habit "${habitName}"? This action cannot be undone.`)) {
        window.location.href = `/habits/delete/${habitId}`;
    }
}

function toggleHabitStatus(habitId, currentStatus) {
    console.log(`Toggling habit ${habitId} from ${currentStatus} to ${currentStatus === 'active' ? 'paused' : 'active'}`);
    // Implement status toggle logic
}