<!-- Samples are offered in a sequence of markdown code blocks -->
{% assign samples_split = samples | remove_first: "```" | split: "```
```" %}

<!-- We need a random number to provide a unique id. We can use nanoseconds -->
{% assign pseudo_random_number = "now" | date: "%09N" %}
{% assign fs_id = "dc-code-" | append: pseudo_random_number %}

<!-- Figure out what is the first code tag, for example js or py -->
{% assign first_cleaned = samples_split[0] | remove: "```" | strip | split: "
" %}
{% assign first_tag = first_cleaned | first %}

<!-- The actual code tabs element -->
<div class="codetabs outer">
<fieldset class="codetabs inner" name="{{fs_id}}">

{% for sample in samples_split %}
{% assign cleaned = sample | remove: "```" | strip | split: "
" %}

{% assign amount_of_code_rows = cleaned | size %}

{% assign tag = cleaned | first %}
{% assign code = cleaned | slice: 1, amount_of_code_rows | join: "
" | strip %}
{% assign id = fs_id | append: "-" | append: tag %}

{% if first_tag == tag %}
{% assign checked = "checked" %}
{% else %}
{% assign checked = "" %}
{% endif %}

<input type="radio" name="{{fs_id}}" id="{{ id }}" value="{{ tag }}" {{ checked }} />
<label for="{{id}}">

{% case tag %}
{% when "py" %}
Python
{% when "js" %}
JavaScript
{% when "ts" %}
TypeScript
{% when "PHP" %}
PHP
{% when "php" %}
PHP
{% when "csharp" %}
C#
{% when "curl" %}
cURL
{% else %}
{{ tag | downcase | capitalize }}
{% endcase %}

</label>

```{{ tag }}
{{ code }}
```
{% endfor %}

</fieldset>
</div>
