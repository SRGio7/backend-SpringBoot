const products = [
  // Electronics
  {
    id: 1,
    name: "Wireless Headphones",
    price: 199.99,
    image: "./assets/Wireless Headphones.jpg",
    category: "electronics",
    description: "Noise-cancelling wireless headphones",
  },
  {
    id: 2,
    name: "Smart Watch",
    price: 299.99,
    image: "./assets/Smart Watch.jpg",
    category: "electronics",
    description: "Fitness tracking smart watch",
  },
  {
    id: 3,
    name: "4K Camera",
    price: 799.99,
    image: "./assets/4k Camera.jpg",
    category: "electronics",
    description: "Professional 4K video camera",
  },

  // Fashion
  {
    id: 4,
    name: "Leather Jacket",
    price: 189.99,
    image: "./assets/Leather Jacket.jpg",
    category: "fashion",
    description: "Premium genuine leather jacket",
  },
  {
    id: 5,
    name: "Designer Sunglasses",
    price: 149.99,
    image: "./assets/Designer Sunglasses.jpg",
    category: "fashion",
    description: "UV protection polarized lenses",
  },
  {
    id: 6,
    name: "Sports Shoes",
    price: 89.99,
    image: "./assets/Sports Shoes.jpg",
    category: "fashion",
    description: "High-performance running shoes",
  },

  // Home & Kitchen
  {
    id: 7,
    name: "Air Fryer",
    price: 129.99,
    image: "./assets/Air Fryer.jpg",
    category: "home",
    description: "Digital touchscreen air fryer",
  },
  {
    id: 8,
    name: "Robot Vacuum",
    price: 299.99,
    image: "./assets/Robot Vacuum.jpg",
    category: "home",
    description: "Smart mapping robot vacuum",
  },
  {
    id: 9,
    name: "Blender",
    price: 79.99,
    image: "./assets/Blender.jpg",
    category: "home",
    description: "1500W professional blender",
  },

  // Sports
  {
    id: 10,
    name: "Yoga Mat",
    price: 29.99,
    image: "./assets/Yoga Mat.jpg",
    category: "sports",
    description: "Eco-friendly non-slip yoga mat",
  },
  {
    id: 11,
    name: "Dumbbell Set",
    price: 149.99,
    image: "./assets/Dumbbell Set.jpg",
    category: "sports",
    description: "Adjustable weight dumbbells",
  },
  {
    id: 12,
    name: "Cycling Helmet",
    price: 59.99,
    image: "./assets/Cycling Helmet.jpg",
    category: "sports",
    description: "Aero-dynamic bicycle helmet",
  },
];

// Initialize products
function initProducts() {
  const container = document.getElementById("products-container");
  products.forEach((product) => {
    const productCard = document.createElement("div");
    productCard.className = "product-card";
    productCard.innerHTML = `
            <img src="${product.image}" alt="${product.name}" class="product-image">
            <h2 class="product-title">${product.name}</h2>
            <p class="product-description">${product.description}</p>
            <p class="product-price">$${product.price}</p>
            <button class="add-to-cart" onclick="addToCart(${product.id})">Add to Cart</button>
        `;
    container.appendChild(productCard);
  });
}

// Cart functionality
function addToCart(productId) {
  const product = products.find((p) => p.id === productId);
  const existingItem = cart.find((item) => item.id === productId);

  if (existingItem) {
    existingItem.quantity++;
  } else {
    cart.push({ ...product, quantity: 1 });
  }

  updateCartDisplay();
  saveCartToLocalStorage();
}

function removeFromCart(productId) {
  cart = cart.filter((item) => item.id !== productId);
  updateCartDisplay();
  saveCartToLocalStorage();
}

function updateCartDisplay() {
  const cartItems = document.getElementById("cart-items");
  const cartCount = document.getElementById("cart-count");
  const cartTotal = document.getElementById("cart-total");

  cartItems.innerHTML = "";
  let total = 0;

  cart.forEach((item) => {
    total += item.price * item.quantity;
    const cartItem = document.createElement("div");
    cartItem.className = "cart-item";
    cartItem.innerHTML = `
            <div style="display: flex; align-items: center;">
                <img src="${item.image}" alt="${item.name}">
                <div>
                    <h3>${item.name}</h3>
                    <p>$${item.price} x ${item.quantity}</p>
                </div>
            </div>
            <button class="remove-item" onclick="removeFromCart(${item.id})">×</button>
        `;
    cartItems.appendChild(cartItem);
  });

  cartCount.textContent = cart.reduce((sum, item) => sum + item.quantity, 0);
  cartTotal.textContent = total.toFixed(2);
}

function toggleCart() {
  document.getElementById("cart-sidebar").classList.toggle("active");
}

function saveCartToLocalStorage() {
  localStorage.setItem("cart", JSON.stringify(cart));
}

// Initialize on page load
document.addEventListener("DOMContentLoaded", () => {
  initProducts();
  updateCartDisplay();
});

let cart = JSON.parse(localStorage.getItem("cart")) || [];
let activeCategory = "all";
let searchQuery = "";

// Initialize store
function initStore() {
  displayProducts();
  setupEventListeners();
  updateCartDisplay();
}

// Event listeners setup
function setupEventListeners() {
  // Search functionality
  document.getElementById("search-input").addEventListener("input", (e) => {
    searchQuery = e.target.value.toLowerCase();
    displayProducts();
  });

  // Category filter
  document.querySelectorAll(".category-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      document
        .querySelectorAll(".category-btn")
        .forEach((b) => b.classList.remove("active"));
      btn.classList.add("active");
      activeCategory = btn.dataset.category;
      displayProducts();
    });
  });

  // Payment method selection
  document.querySelectorAll(".payment-method").forEach((btn) => {
    btn.addEventListener("click", () => {
      document
        .querySelectorAll(".payment-method")
        .forEach((b) => b.classList.remove("active"));
      btn.classList.add("active");
    });
  });
}

// Filter and display products
function displayProducts() {
  const container = document.getElementById("products-container");
  container.innerHTML = "";

  const filteredProducts = products.filter((product) => {
    const matchesCategory =
      activeCategory === "all" || product.category === activeCategory;
    const matchesSearch =
      product.name.toLowerCase().includes(searchQuery) ||
      product.description.toLowerCase().includes(searchQuery);
    return matchesCategory && matchesSearch;
  });

  filteredProducts.forEach((product) => {
    const productCard = document.createElement("div");
    productCard.className = "product-card";
    productCard.innerHTML = `
            <img src="${product.image}" alt="${product.name}" class="product-image">
            <h2 class="product-title">${product.name}</h2>
            <p class="product-description">${product.description}</p>
            <p class="product-price">$${product.price}</p>
            <button class="add-to-cart" onclick="addToCart(${product.id})">Add to Cart</button>
        `;
    container.appendChild(productCard);
  });
}

// Payment functionality
function showPaymentModal() {
  if (cart.length === 0) return alert("Your cart is empty!");
  document.getElementById("payment-total").textContent = cart
    .reduce((sum, item) => sum + item.price * item.quantity, 0)
    .toFixed(2);
  document.getElementById("payment-modal").style.display = "block";
}

function closePaymentModal() {
  document.getElementById("payment-modal").style.display = "none";
  resetPaymentUI();
}

function resetPaymentUI() {
  document.getElementById("payment-form").reset();
  document.getElementById("payment-processing").style.display = "none";
  document.getElementById("payment-success").style.display = "none";
  document.getElementById("payment-form").style.display = "block";
}

function processPayment(e) {
  e.preventDefault();

  // Show processing animation
  document.getElementById("payment-form").style.display = "none";
  document.getElementById("payment-processing").style.display = "block";

  // Simulate payment processing
  setTimeout(() => {
    document.getElementById("payment-processing").style.display = "none";
    document.getElementById("payment-success").style.display = "block";

    // Clear cart after successful payment
    setTimeout(() => {
      cart = [];
      localStorage.removeItem("cart");
      updateCartDisplay();
      closePaymentModal();
    }, 2000);
  }, 3000);
}

// Initialize store on load
document.addEventListener("DOMContentLoaded", initStore);
